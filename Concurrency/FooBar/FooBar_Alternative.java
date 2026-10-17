import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class FooBar_Alternative{

    static int n = 10;

    public static Semaphore fooSemaphore = new Semaphore(1);
    public static Semaphore barSemaphore = new Semaphore(0);

    public static void main(String[] args){
        ExecutorService executors = Executors.newFixedThreadPool(2);

        CompletableFuture.runAsync(() -> bar(), executors);
        CompletableFuture.runAsync(() -> foo(), executors);

        executors.shutdown();
    }


    private static void foo(){
        for(int i = 0; i < n; i++){
            try {
                fooSemaphore.acquire();
                System.out.print("foo");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                barSemaphore.release();
            }
        }
    }

    private static void bar(){
        for(int i = 0; i < n; i++){
             try {
                barSemaphore.acquire();
                System.out.print("bar");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                fooSemaphore.release();
            }
        }
    }
}


