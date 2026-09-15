from Product import Product
from PaymentStrategy.IPaymentStrategy import IPaymentStrategy


class Transaction:
    def __init__(self):
        self.selected_product: Product | None = None
        self.amount_inserted: int = 0
        self.payment_strategy: IPaymentStrategy | None = None
