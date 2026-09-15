from Coin import Coin
from TransactionState import TransactionState
from State.IVendingMachineState import IVendingMachineState


class CompletedState(IVendingMachineState):
    def __init__(self, machine):
        self.machine = machine

    def getState(self) -> TransactionState:
        return TransactionState.COMPLETED

    def selectProduct(self, code: str) -> None:
        raise ValueError("Transaction already completed.")

    def insertCoin(self, coin: Coin) -> None:
        raise ValueError("Transaction already completed.")

    def processTransaction(self) -> None:
        raise ValueError("Transaction already completed.")
