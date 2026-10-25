import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class OrderManagementSystemTest {

    private Inventory inventory;
    private OrderManagementSystem oms;

    @BeforeEach
    void setUp() {
        OrderManagementSystem._omsInstance = null;

        inventory = new Inventory();
        // Reset or initialize singleton instance for tests if needed, 
        // or ensure your design allows fresh initialization per test.
        inventory.addItem(new Item(1, "Apple"), 10); // Item 1 has 10 units
        inventory.addItem(new Item(2, "Orange"), 2);  // Item 2 has 2 units
        
        oms = OrderManagementSystem.getInstance(inventory);
    }

    @Test
    void testPlaceOrder_Success() {
        Map<Integer, Integer> items = Map.of(1, 3); // Request 3 of Item 1
        Order order = new Order(101);
        order.items = items;

        boolean result = oms.placeOrder(order);

        assertTrue(result);
        assertEquals(OrderState.COMPLETED, order.orderState);
        assertEquals(7, inventory.itemsCount.get(1).get()); // 10 - 3 = 7
    }

    @Test
    void testPlaceOrder_OutOfStock() {
        Map<Integer, Integer> items = Map.of(1, 15); // Request 15, but only 10 available
        Order order = new Order(102);
        order.items = items;

        boolean result = oms.placeOrder(order);

        assertFalse(result);
        assertEquals(OrderState.PENDING, order.orderState);
        assertEquals(10, inventory.itemsCount.get(1).get()); // Stock should remain untouched
    }

    @Test
    void testPlaceOrder_MultiItemRollback() {
        // Order 2 items: 
        // Item 1 (has 10, request 3) -> Should pass
        // Item 2 (has 2, request 5)  -> Should fail (out of stock)
        // Because Item 2 fails, Item 1 MUST be rolled back back to 10.

        Order order = new Order(103);
        order.items.put(1, 3); 
        order.items.put(2, 5); 

        boolean result = oms.placeOrder(order);

        assertFalse(result);
        assertEquals(OrderState.PENDING, order.orderState);
        
        // Verify rollback happened! Item 1 should be back to 10, not 7.
        assertEquals(10, inventory.itemsCount.get(1).get(), "Item 1 should have rolled back to 10");
        assertEquals(2, inventory.itemsCount.get(2).get(), "Item 2 stock should be untouched");
    }

    @Test
    void testPlaceOrder_ConcurrentStress() throws InterruptedException {
        // Item 1 starts with 10 units. 
        // Fire 20 threads simultaneously, each trying to buy 1 unit of Item 1.
        // Exactly 10 should succeed, and 10 should fail. Final stock must be 0.
        
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int orderId = i;
            executor.submit(() -> {
                try {
                    Order order = new Order(orderId);
                    order.items.put(1,1);

                    if (oms.placeOrder(order)) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Assertions
        assertEquals(10, successCount.get(), "Exactly 10 orders should succeed based on stock");
        assertEquals(10, failCount.get(), "Exactly 10 orders should fail due to stock exhaustion");
        assertEquals(0, inventory.itemsCount.get(1).get(), "Final stock must be exactly 0, never negative");
    }
}