from PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class NetBankingPayment(IPaymentStrategy):
    def pay(self, amount: float):
        print(f"Paid {amount} using Net Banking")