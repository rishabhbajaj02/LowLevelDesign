import java.util.concurrent.ConcurrentHashMap;

public class TokenBucket implements RateLimitingStrategy {

    private final int capacity;
    private final double refillRatePerMs; // Using ms avoids second truncation issues
    private final ConcurrentHashMap<String, UserBucket> userBuckets = new ConcurrentHashMap<>();

    public TokenBucket(int capacity, int refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerMs = (double) refillRatePerSecond / 1000.0;
    }

    @Override
    public boolean allowRequest(String userId) {
        UserBucket userBucket = userBuckets.computeIfAbsent(
            userId, 
            k -> new UserBucket(capacity, System.currentTimeMillis())
        );

        synchronized (userBucket) {
            refill(userBucket);

            if (userBucket.getTokens() >= 1.0) {
                userBucket.setTokens(userBucket.getTokens() - 1.0);
                return true;
            }
            return false;
        }
    }

    private void refill(UserBucket userBucket) {
        long now = System.currentTimeMillis();
        long timeElapsed = now - userBucket.getLastRefillTimestamp();

        // FIX: Only update timestamp when actual time has elapsed
        if (timeElapsed > 0) {
            double tokensToAdd = timeElapsed * refillRatePerMs;
            double updatedTokens = Math.min(capacity, userBucket.getTokens() + tokensToAdd);

            userBucket.setTokens(updatedTokens);
            userBucket.setLastRefillTimestamp(now);
        }
    }

    public static class UserBucket {
        private double tokens;
        private long lastRefillTimestamp;

        public UserBucket(double tokens, long lastRefillTimestamp) {
            this.tokens = tokens;
            this.lastRefillTimestamp = lastRefillTimestamp;
        }

        public double getTokens() { return tokens; }
        public void setTokens(double tokens) { this.tokens = tokens; }
        public long getLastRefillTimestamp() { return lastRefillTimestamp; }
        public void setLastRefillTimestamp(long lastRefillTimestamp) { this.lastRefillTimestamp = lastRefillTimestamp; }
    }
}