from Coin import Coin
from TransactionState import TransactionState
from State.IVendingMachineState import IVendingMachineState


class InProgressState(IVendingMachineState):
    def __init__(self, machine):
        self.machine = machine

    def getState(self) -> TransactionState:
        return TransactionState.IN_PROGRESS

    def selectProduct(self, code: str) -> None:
        raise ValueError("Product already selected.")

    def insertCoin(self, coin: Coin) -> None:
        self.machine.transaction.amount_inserted += coin.value
        self.machine.cash_inventory.addCoin(coin)
        self.machine.notifyObservers("COIN_INSERTED", {"coin": coin.value, "total": self.machine.transaction.amount_inserted})
        print(f"Inserted coin: {coin.value}. Total inserted: {self.machine.transaction.amount_inserted}")

    def processTransaction(self) -> None:
        product = self.machine.transaction.selected_product

        if not self.machine.product_inventory.isAvailable(product.code):
            self._cancelTransaction("Product went out of stock.")
            raise ValueError(f"Product {product.name} is out of stock.")

        strategy = self.machine.transaction.payment_strategy
        if strategy is None:
            raise ValueError("No payment strategy set.")

        if not strategy.pay(self.machine):
            self._cancelTransaction("Payment failed.")
            raise ValueError(f"Insufficient amount for {product.name}.")

        change = self.machine.transaction.amount_inserted - product.price
        self.machine.dispenseProduct()
        if change > 0:
            self.machine.returnChange(change)

        from State.CompletedState import CompletedState
        self.machine.setState(CompletedState(self.machine))
        self.machine.notifyObservers("TRANSACTION_COMPLETED", {"product": product.name})
        print(f"Transaction completed for {product.name}.")
        self.machine.resetTransaction()

    def _cancelTransaction(self, reason: str) -> None:
        refund = self.machine.transaction.amount_inserted
        from State.CancelledState import CancelledState
        self.machine.setState(CancelledState(self.machine))
        self.machine.notifyObservers("TRANSACTION_CANCELLED", {"reason": reason, "refund": refund})
        if refund > 0:
            print(f"Transaction cancelled. Refunding: {refund}")
        else:
            print("Transaction cancelled.")
        self.machine.resetTransaction()
