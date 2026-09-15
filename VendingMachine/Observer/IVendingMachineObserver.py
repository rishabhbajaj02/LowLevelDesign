from abc import ABC, abstractmethod


class IVendingMachineObserver(ABC):
    @abstractmethod
    def update(self, event: str, data: dict) -> None:
        pass
