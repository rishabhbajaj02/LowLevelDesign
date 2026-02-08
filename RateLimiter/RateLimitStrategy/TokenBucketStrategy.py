from RateLimitStrategy.IRateLimitStrategy import IRateLimitStrategy
from RateLimitStrategy.Bucket import Bucket

class TokenBucketStrategy(IRateLimitStrategy):
    def __init__(self, capacity: int, refill_rate: int):
        self.buckets = {}
        self.capacity = capacity
        self.refill_rate = refill_rate

    def allowRequest(self, key: str) -> bool:
        if key not in self.buckets:
            self.buckets[key] = Bucket(self.capacity, self.refill_rate)
        return self.buckets[key].consume(1)