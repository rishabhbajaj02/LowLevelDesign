import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindow implements RateLimitingStrategy{

    private int windowSizeInSeconds;

    private int requestLimit;

    private ConcurrentHashMap<String, Deque<Long>> requestCounts = new ConcurrentHashMap<>();

    public SlidingWindow(int requestLimit, int windowSizeInSeconds) {
        this.requestLimit = requestLimit;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    @Override
    public boolean allowRequest(String userId) {
        
        long currentTime = (long) (System.currentTimeMillis() / 1000);

        long windowBoundary = currentTime - windowSizeInSeconds;

        Deque<Long> timestamps = requestCounts.computeIfAbsent(userId, k -> new ArrayDeque<>());

        synchronized(timestamps){

            while(!timestamps.isEmpty() && timestamps.peekFirst() <= windowBoundary ){
                timestamps.pollFirst();
            }

            if(timestamps.size() < requestLimit){
                timestamps.addLast(currentTime);
                return true;
            }

            return false; // rate limit exceeded
        }
    }
}