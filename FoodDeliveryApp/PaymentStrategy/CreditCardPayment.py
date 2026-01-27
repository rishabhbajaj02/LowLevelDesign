from PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class CreditCardPayment(IPaymentStrategy):
    def pay(self, amount: float):
        print(f"Paid {amount} using Credit Card")