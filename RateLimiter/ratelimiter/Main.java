import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 5;               // Max 5 tokens allowed in burst
        int refillRatePerSecond = 2;    // Refills 2 tokens per second
        
        RateLimitingStrategy rateLimiter = new SlidingWindow(capacity, 1);

        int numberOfThreads = 10;
        int requestsPerThread = 2; // Total 20 requests hitting concurrently
        
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        // Latch 1: Holds back all threads until everyone is ready to release at once
        CountDownLatch startSignal = new CountDownLatch(1);
        
        // Latch 2: Main thread waits for all requests to finish before exiting
        CountDownLatch doneSignal = new CountDownLatch(numberOfThreads * requestsPerThread);

        // Track pass/fail metrics atomically across threads
        AtomicInteger allowedCount = new AtomicInteger(0);
        AtomicInteger blockedCount = new AtomicInteger(0);

        System.out.println("--- Starting Concurrent Test ---");
        System.out.println("Bucket Capacity: " + capacity);

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Wait at the starting line until startSignal counts down to 0
                    startSignal.await();

                    for (int j = 0; j < requestsPerThread; j++) {
                        String userId = "user_1"; // Testing concurrent requests for SAME user
                        boolean allowed = rateLimiter.allowRequest(userId);

                        if (allowed) {
                            allowedCount.incrementAndGet();
                            System.out.println(Thread.currentThread().getName() + " -> Allowed");
                        } else {
                            blockedCount.incrementAndGet();
                            System.out.println(Thread.currentThread().getName() + " -> BLOCKED");
                        }
                        
                        doneSignal.countDown();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Release all threads simultaneously
        startSignal.countDown();

        // Wait until all threads finish executing
        doneSignal.await();
        executor.shutdown();

        System.out.println("\n--- Burst Results ---");
        System.out.println("Total Allowed: " + allowedCount.get() + " (Expected: " + capacity + ")");
        System.out.println("Total Blocked: " + blockedCount.get() + " (Expected: " + (numberOfThreads * requestsPerThread - capacity) + ")");

        // Wait 1 second to observe refill behavior
        System.out.println("\nWaiting 1000ms for bucket to refill...");
        Thread.sleep(1000);

        boolean postRefillRequest = rateLimiter.allowRequest("user_1");
        System.out.println("Request after 1s refill allowed? " + postRefillRequest + " (Expected: true)");
    }
}