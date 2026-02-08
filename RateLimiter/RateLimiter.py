from RateLimitStrategy.IRateLimitStrategy import IRateLimitStrategy
class RateLimiter:
    def __init__(self, resolver: IRateLimitStrategy):
        self.resolver = resolver

    def allowRequest(self, key: str) -> bool:
        return self.resolver.allowRequest(key)