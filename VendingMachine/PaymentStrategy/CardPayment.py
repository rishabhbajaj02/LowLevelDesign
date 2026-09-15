from PaymentStrategy.IPaymentStrategy import IPaymentStrategy


class CardPayment(IPaymentStrategy):
    def __init__(self, card_number: str):
        self.card_number = card_number

    def pay(self, machine) -> bool:
        price = machine.transaction.selected_product.price
        print(f"Charging {price} to card ending in {self.card_number[-4:]}")
        return True
