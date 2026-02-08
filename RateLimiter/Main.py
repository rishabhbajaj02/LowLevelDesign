from RateLimiter import RateLimiter
from RateLimitStrategy.TokenBucketStrategy import TokenBucketStrategy
from RateLimitStrategy.FixedWindowStrategy import FixedWindowStrategy
import time

class Main:
    def main(self):
        rate_limiter = RateLimiter(TokenBucketStrategy(capacity=10, refill_rate=1))
        user_id = "usr_123"
        for i in range(100):
            if rate_limiter.allowRequest(user_id):
                print(f"Request {i+1} allowed")
            else:
                print(f"Request {i+1} denied")
            time.sleep(0.2)

        rate_limiter = RateLimiter(FixedWindowStrategy(capacity=5, window_size=5))
        user_id = "usr_123"
        for i in range(100):
            if rate_limiter.allowRequest(user_id):
                print(f"Request {i+1} allowed")
            else:
                print(f"Request {i+1} denied")
            time.sleep(0.2)

if __name__ == "__main__":
    main = Main()
    main.main()