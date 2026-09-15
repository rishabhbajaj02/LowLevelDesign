from Coin import Coin
from TransactionState import TransactionState
from State.IVendingMachineState import IVendingMachineState


class OutOfStockState(IVendingMachineState):
    def __init__(self, machine):
        self.machine = machine

    def getState(self) -> TransactionState:
        return TransactionState.OUT_OF_STOCK

    def selectProduct(self, code: str) -> None:
        raise ValueError("Product is out of stock.")

    def insertCoin(self, coin: Coin) -> None:
        raise ValueError("Product is out of stock.")

    def processTransaction(self) -> None:
        raise ValueError("Product is out of stock.")
