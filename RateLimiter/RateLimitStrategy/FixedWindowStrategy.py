from RateLimitStrategy.IRateLimitStrategy import IRateLimitStrategy
import time

class FixedWindowStrategy(IRateLimitStrategy):
    def __init__(self, capacity: int, window_size: int):
        self.capacity = capacity
        self.window_size = window_size
        self.requests = {}

    def allowRequest(self, key: str) -> bool:
        current_time = time.time()
        window_start = int(current_time / self.window_size) * self.window_size
        if key not in self.requests:
            self.requests[key] = []
        self.requests[key] = [t for t in self.requests[key] if t >= window_start]
        if len(self.requests[key]) < self.capacity:
            self.requests[key].append(current_time)
            return True
        return False