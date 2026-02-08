from threading import Lock
import time

class Bucket:
    def __init__(self, capacity: int, refill_rate: int):
        self.capacity = capacity
        self.refill_rate = refill_rate
        self.tokens = capacity
        self.last_refill_time = time.time()
        self.lock = Lock()

    def refill(self):
        now = time.time()
        time_passed = now - self.last_refill_time
        tokens_to_add = time_passed * self.refill_rate
        self.tokens = min(self.capacity, self.tokens + tokens_to_add)
        self.last_refill_time = now

    def consume(self, tokens: int) -> bool:
        with self.lock:
            self.refill()
        
        if self.tokens >= tokens:
            self.tokens -= tokens
            return True
        return False