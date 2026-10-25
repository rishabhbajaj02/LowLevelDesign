import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws Exception {
        

        Inventory inventory = new Inventory();

        Item item1 = new Item(1, "Apple");
        Item item2 = new Item(2, "Banana");
        Item item3 = new Item(3, "Grapes");
        Item item4 = new Item(4, "Pear");
        Item item5 = new Item(5, "Orange");

        inventory.addItem(item1, 4);
        inventory.addItem(item2, 2);
        inventory.addItem(item3, 3);
        inventory.addItem(item4, 5);
        inventory.addItem(item5, 5);


        var system = OrderManagementSystem.getInstance(inventory);

        Order order1 =  new Order(1);
        Order order2 =  new Order(2);
        Order order3 =  new Order(3);

        order1.addItem(item1);
        order1.addItem(item2);
        order1.addItem(item1);

        order3.addItem(item5);
        order3.addItem(item3);
        order3.addItem(item3);

        order2.addItem(item3);
        order2.addItem(item3);
        order2.addItem(item4);


        List<Order> orders = List.of(order1, order2, order3);

        system.printInventory();


        int numThreads = 3;

        ExecutorService ex = Executors.newFixedThreadPool(numThreads);
        CountDownLatch cdl = new CountDownLatch(numThreads);

        for(int i = 0; i < numThreads; i++){
            var c = i;
            ex.submit(() -> {
                try{
                    var order = orders.get(c);

                    boolean success = system.placeOrder(order);
                    
                    if(success){
                        System.out.println(order.id + ": "+ "DONE");
                    }else{
                        System.out.println(order.id + ": "+ "FAILED");
                    }
                }catch(Exception exc){
                    throw exc;
                }finally{
                    cdl.countDown();
                }
            });

        }


        cdl.await();
        ex.shutdown();


        system.printInventory();
    }
}
