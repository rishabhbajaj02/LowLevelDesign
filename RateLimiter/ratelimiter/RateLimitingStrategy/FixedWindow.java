import java.util.HashMap;
import java.util.Map;

public class FixedWindow implements RateLimitingStrategy{

    private Map<String, Integer> requestCounts = new HashMap<>();

    private int requestLimit;

    private int windowSizeInSeconds;

    private long currentWindowStartTime;

    public FixedWindow(int requestLimit, int windowSizeInSeconds) {
        this.requestLimit = requestLimit;
        this.windowSizeInSeconds = windowSizeInSeconds;
        long currentTime = (long) (System.currentTimeMillis() / 1000);
        this.currentWindowStartTime = (currentTime /  windowSizeInSeconds) * windowSizeInSeconds;
    }


    @Override
    public synchronized boolean allowRequest(String userId) {
        // if current window start time + windowSize has passed
        // reset the window and request limit

        // else
        // check request limit and decrease
        // if request limit exceeded, deny the request

        long currentTime = (long) (System.currentTimeMillis() / 1000);
        if (currentTime >= currentWindowStartTime + windowSizeInSeconds) {
            currentWindowStartTime = (currentTime /  windowSizeInSeconds) * windowSizeInSeconds;
            requestCounts.clear(); // reset the request counts for all users
        }

        int userRequestCount = requestCounts.getOrDefault(userId, 0);
        if (userRequestCount < requestLimit) {
            requestCounts.put(userId, userRequestCount + 1);
            return true;
        } else {
            return false;
        }
    }
}