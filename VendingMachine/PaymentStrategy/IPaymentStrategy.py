from abc import ABC, abstractmethod


class IPaymentStrategy(ABC):
    @abstractmethod
    def pay(self, machine) -> bool:
        pass
