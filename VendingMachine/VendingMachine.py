from threading import Lock

from Coin import Coin
from ProductInventory import ProductInventory
from CashInventory import CashInventory
from Transaction import Transaction
from State.IVendingMachineState import IVendingMachineState
from State.NotStartedState import NotStartedState
from Observer.IVendingMachineObserver import IVendingMachineObserver
from PaymentStrategy.IPaymentStrategy import IPaymentStrategy


class VendingMachine:
    _instance = None

    def __new__(cls, *args, **kwargs):
        if cls._instance is None:
            cls._instance = super(VendingMachine, cls).__new__(cls)
        return cls._instance

    def __init__(self, product_inventory: ProductInventory, cash_inventory: CashInventory):
        if hasattr(self, 'initialized'):
            return
        self.product_inventory = product_inventory
        self.cash_inventory = cash_inventory
        self.transaction = Transaction()
        self.lock = Lock()
        self.current_state: IVendingMachineState = NotStartedState(self)
        self.observers: list[IVendingMachineObserver] = []
        self.initialized = True

    def setState(self, state: IVendingMachineState) -> None:
        self.current_state = state

    def addObserver(self, observer: IVendingMachineObserver) -> None:
        self.observers.append(observer)

    def removeObserver(self, observer: IVendingMachineObserver) -> None:
        self.observers.remove(observer)

    def notifyObservers(self, event: str, data: dict) -> None:
        for observer in self.observers:
            observer.update(event, data)

    def selectProduct(self, code: str) -> None:
        with self.lock:
            self.current_state.selectProduct(code)

    def insertCoin(self, coin: Coin) -> None:
        with self.lock:
            self.current_state.insertCoin(coin)

    def setPaymentStrategy(self, strategy: IPaymentStrategy) -> None:
        with self.lock:
            self.transaction.payment_strategy = strategy

    def processTransaction(self) -> None:
        with self.lock:
            self.current_state.processTransaction()

    def dispenseProduct(self) -> None:
        product = self.transaction.selected_product
        product.quantity -= 1
        print(f"Dispensing product: {product.name}")
        self.notifyObservers("PRODUCT_DISPENSED", {"product": product.name, "remaining": product.quantity})
        if product.quantity <= 2:
            self.notifyObservers("LOW_INVENTORY", {"product": product.name, "remaining": product.quantity})

    def returnChange(self, change: int) -> None:
        print(f"Returning change: {change}")

    def resetTransaction(self) -> None:
        self.transaction = Transaction()
        self.current_state = NotStartedState(self)
