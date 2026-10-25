import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderManagementSystem{

    private Inventory inventory;

    public static volatile OrderManagementSystem _omsInstance;

    private OrderManagementSystem(Inventory inventory){
        this.inventory = inventory;
    }

    public static OrderManagementSystem getInstance(Inventory inventory){

        if(_omsInstance == null){
            synchronized(OrderManagementSystem.class){
                if(_omsInstance == null){
                    _omsInstance = new OrderManagementSystem(inventory);
                }
            }
        }

        return _omsInstance;

    }

    public static OrderManagementSystem getInstance() {
        if (_omsInstance == null) {
            throw new IllegalStateException("OrderManagementSystem is not initialized yet. Call getInstance(inventory) first.");
        }
        return _omsInstance;
    }


    public boolean placeOrder(Order order){

        Map<Integer, Integer> items = order.items; 

        order.orderState = OrderState.PROCESSING;

        List<Integer> successfullyDeductedItems = new ArrayList<>();

        for(var itemId: items.keySet()){
            
            boolean success = false;

            while(!success){
                var itemCount = inventory.itemsCount.get(itemId);
                
                var available = itemCount.get();
                var requested = items.get(itemId);

                var diff = available - requested;

                if(diff < 0){
                    order.orderState = OrderState.PENDING;
                    rollbackUpdatedItems(items, successfullyDeductedItems);
                    return false;
                    // order has more items requested than available in inventory
                }

                if(itemCount.compareAndSet(available, diff)){
                   success = true;
                   successfullyDeductedItems.add(itemId);
                }else{
                    continue;
                }

            }

        }

        order.orderState = OrderState.COMPLETED;

        return true;
    }

    private void rollbackUpdatedItems(Map<Integer, Integer> items, List<Integer> deductedItems){

        for(int itemId: deductedItems){
            
            var count = items.get(itemId);

            inventory.itemsCount.get(itemId).addAndGet(count);

        }

    }


    public void printInventory(){
        inventory.display();
    }

}