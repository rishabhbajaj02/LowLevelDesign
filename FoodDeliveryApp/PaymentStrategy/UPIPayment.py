from PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class UPIPayment(IPaymentStrategy):
    def pay(self, amount: float):
        print(f"Paid {amount} using UPI")