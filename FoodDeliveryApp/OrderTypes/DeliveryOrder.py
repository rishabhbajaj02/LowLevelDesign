from Order import Order
from User import User
from Restaurant import Restaurant
from MenuItem import MenuItem
from PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class DeliveryOrder(Order):
    def __init__(self, id: int, user: User, restaurant: Restaurant, items: list[MenuItem], payment_strategy: IPaymentStrategy):
        super().__init__(id, user, restaurant, items, payment_strategy)

    def getType(self) -> str:
        return "DeliveryOrder"