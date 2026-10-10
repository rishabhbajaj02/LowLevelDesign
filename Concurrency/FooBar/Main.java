import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;  

public class Main{

    static int n = 10;
    private final static Lock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();
    private static boolean isThreadOneTurn = true; 

    public static void main(String[] args){
        ExecutorService executors = Executors.newFixedThreadPool(2);

        CompletableFuture.runAsync(() -> bar(), executors);
        CompletableFuture.runAsync(() -> foo(), executors);

        executors.shutdown();
    }


    private static void foo(){
        for(int i = 0; i < n; i++){
            lock.lock();
            try {
                if(!isThreadOneTurn){
                    condition.await();
                }
                // critical section
                System.out.print("foo");
                isThreadOneTurn = false;
                condition.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void bar(){
        for(int i = 0; i < n; i++){
            lock.lock();
            try {
                if(isThreadOneTurn){
                    condition.await();
                }
                // critical section
                System.out.print("bar");
                isThreadOneTurn = true;
                condition.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}


