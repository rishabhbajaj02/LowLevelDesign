import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Inventory{
    public Map<Integer, Item> itemsMap;
    public Map<Integer, AtomicInteger> itemsCount;


    public Inventory(){
        this.itemsMap = new HashMap<>();
        this.itemsCount = new HashMap<>();
    }

    public void addItem(Item item, int count){
        itemsMap.putIfAbsent(item.id, item);
        itemsCount.putIfAbsent(item.id, new AtomicInteger(0));

        itemsCount.get(item.id).getAndAdd(count);
    }

    public void display(){

        for(int key: itemsMap.keySet()){
            System.out.println(itemsMap.get(key).name + ": "+ itemsCount.get(key).get());
        }


    }
}