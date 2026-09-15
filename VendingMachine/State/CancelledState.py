from Coin import Coin
from TransactionState import TransactionState
from State.IVendingMachineState import IVendingMachineState


class CancelledState(IVendingMachineState):
    def __init__(self, machine):
        self.machine = machine

    def getState(self) -> TransactionState:
        return TransactionState.CANCELLED

    def selectProduct(self, code: str) -> None:
        raise ValueError("Transaction was cancelled. Start a new transaction.")

    def insertCoin(self, coin: Coin) -> None:
        raise ValueError("Transaction was cancelled. Start a new transaction.")

    def processTransaction(self) -> None:
        raise ValueError("Transaction was cancelled. Start a new transaction.")
