from Coin import Coin
from Product import Product
from ProductInventory import ProductInventory
from CashInventory import CashInventory
from VendingMachine import VendingMachine
from PaymentStrategy.CoinPayment import CoinPayment
from PaymentStrategy.CardPayment import CardPayment
from Observer.InventoryAlertObserver import InventoryAlertObserver
from Observer.TransactionLogObserver import TransactionLogObserver


def main():
    products = [
        Product("A1", "Coke", 10, 5),
        Product("A2", "Pepsi", 8, 3),
        Product("B1", "Chips", 5, 10),
        Product("B2", "Candy", 2, 0),
    ]

    product_inventory = ProductInventory(products)
    cash_inventory = CashInventory()
    cash_inventory.addCoin(Coin.ONE, 10)
    cash_inventory.addCoin(Coin.TWO, 5)
    cash_inventory.addCoin(Coin.FIVE, 5)

    vm = VendingMachine(product_inventory, cash_inventory)

    # Register observers
    inventory_alert = InventoryAlertObserver()
    txn_log = TransactionLogObserver()
    vm.addObserver(inventory_alert)
    vm.addObserver(txn_log)

    # Verify Singleton
    vm2 = VendingMachine(product_inventory, cash_inventory)
    print(f"Singleton check: {vm is vm2}")

    # Transaction 1: Coin payment - exact amount
    print("\n=== Transaction 1: Buy Coke (price 10) with coins ===")
    vm.selectProduct("A1")
    vm.setPaymentStrategy(CoinPayment())
    vm.insertCoin(Coin.FIVE)
    vm.insertCoin(Coin.FIVE)
    vm.processTransaction()

    # Transaction 2: Coin payment - with change
    print("\n=== Transaction 2: Buy Chips (price 5) with change ===")
    vm.selectProduct("B1")
    vm.setPaymentStrategy(CoinPayment())
    vm.insertCoin(Coin.TEN)
    vm.processTransaction()

    # Transaction 3: Card payment
    print("\n=== Transaction 3: Buy Pepsi (price 8) with card ===")
    vm.selectProduct("A2")
    vm.setPaymentStrategy(CardPayment("4111111111111234"))
    vm.processTransaction()

    # Transaction 4: Out of stock
    print("\n=== Transaction 4: Buy Candy (out of stock) ===")
    try:
        vm.selectProduct("B2")
    except ValueError as e:
        print(f"Error: {e}")

    # Transaction 5: Insufficient coins
    print("\n=== Transaction 5: Buy Coke (price 10) insufficient coins ===")
    try:
        vm.selectProduct("A1")
        vm.setPaymentStrategy(CoinPayment())
        vm.insertCoin(Coin.TWO)
        vm.processTransaction()
    except ValueError as e:
        print(f"Error: {e}")

    # Print all logs
    print("\n=== Transaction Logs ===")
    for log in txn_log.logs:
        print(log)


if __name__ == "__main__":
    main()
