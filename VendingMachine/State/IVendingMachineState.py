from abc import ABC, abstractmethod

from Coin import Coin
from TransactionState import TransactionState


class IVendingMachineState(ABC):
    @abstractmethod
    def getState(self) -> TransactionState:
        pass

    @abstractmethod
    def selectProduct(self, code: str) -> None:
        pass

    @abstractmethod
    def insertCoin(self, coin: Coin) -> None:
        pass

    @abstractmethod
    def processTransaction(self) -> None:
        pass
