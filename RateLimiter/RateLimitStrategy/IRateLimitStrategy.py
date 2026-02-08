from abc import ABC, abstractmethod

class IRateLimitStrategy(ABC):
    @abstractmethod
    def allowRequest(self, key: str) -> bool:
        pass