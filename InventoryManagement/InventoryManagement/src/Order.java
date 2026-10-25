import java.util.HashMap;
import java.util.Map;

public class Order {
    public int id;
    public Map<Integer, Integer> items;
    public OrderState orderState;

    public Order(int id){
        this.id = id;
        this.items = new HashMap<>();
        this.orderState = OrderState.PENDING;
    }

    public void addItem(Item item){
        items.put(item.id, items.getOrDefault(item.id, 0)+1);
    }
}
