from PaymentStrategy.IPaymentStrategy import IPaymentStrategy


class CoinPayment(IPaymentStrategy):
    def pay(self, machine) -> bool:
        amount = machine.transaction.amount_inserted
        price = machine.transaction.selected_product.price
        if amount < price:
            print(f"Insufficient coins. Inserted: {amount}, Required: {price}")
            return False
        print(f"Coin payment accepted. Paid: {price}")
        return True
