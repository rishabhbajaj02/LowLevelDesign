import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ZeroEvenOdd{

    static int n = 10;

    public static Semaphore zeroSemaphore = new Semaphore(1);
    public static Semaphore evenSemaphore = new Semaphore(0);
    public static Semaphore oddSemaphore = new Semaphore(0);

    public static void main(String[] args){
        ExecutorService executors = Executors.newFixedThreadPool(3);

        CompletableFuture.runAsync(() -> even(), executors);
        CompletableFuture.runAsync(() -> zero(), executors);
        CompletableFuture.runAsync(() -> odd(), executors);

        executors.shutdown();
    }


    private static void zero(){
        for(int i = 1; i <= n; i++){
            try {
                zeroSemaphore.acquire();
                System.out.print("0");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if(i % 2 == 0){
                    evenSemaphore.release();
                } else {
                    oddSemaphore.release();
                }
            }
        }
    }

    private static void even(){
        for(int i = 2; i <= n; i+=2){
             try {
                evenSemaphore.acquire();
                System.out.print(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                zeroSemaphore.release();
            }
        }
    }

    private static void odd(){
        for(int i = 1; i <= n; i+=2){
             try {
                oddSemaphore.acquire();
                System.out.print(i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                zeroSemaphore.release();
            }
        }
    }
}


