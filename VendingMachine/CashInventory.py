from Coin import Coin


class CashInventory:
    def __init__(self):
        self.coins: dict[Coin, int] = {coin: 0 for coin in Coin}

    def addCoin(self, coin: Coin, count: int = 1) -> None:
        self.coins[coin] += count

    def isAvailable(self, coin: Coin) -> bool:
        return self.coins[coin] > 0

    def getTotal(self) -> int:
        return sum(coin.value * count for coin, count in self.coins.items())
