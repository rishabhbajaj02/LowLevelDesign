from Coin import Coin
from TransactionState import TransactionState
from State.IVendingMachineState import IVendingMachineState


class NotStartedState(IVendingMachineState):
    def __init__(self, machine):
        self.machine = machine

    def getState(self) -> TransactionState:
        return TransactionState.NOT_STARTED

    def selectProduct(self, code: str) -> None:
        if not self.machine.product_inventory.isAvailable(code):
            from State.OutOfStockState import OutOfStockState
            self.machine.setState(OutOfStockState(self.machine))
            self.machine.notifyObservers("OUT_OF_STOCK", {"code": code})
            self.machine.resetTransaction()
            raise ValueError(f"Product {code} is out of stock")

        product = self.machine.product_inventory.getProduct(code)
        self.machine.transaction.selected_product = product
        from State.InProgressState import InProgressState
        self.machine.setState(InProgressState(self.machine))
        self.machine.notifyObservers("PRODUCT_SELECTED", {"product": product.name, "price": product.price})
        print(f"Selected product: {product.name}, Price: {product.price}")

    def insertCoin(self, coin: Coin) -> None:
        raise ValueError("No product selected. Select a product first.")

    def processTransaction(self) -> None:
        raise ValueError("No transaction in progress.")
