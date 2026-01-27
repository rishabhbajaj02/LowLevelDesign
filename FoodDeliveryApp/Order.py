from abc import ABC, abstractmethod
from MenuItem import MenuItem
from Restaurant import Restaurant
from User import User
from PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class Order(ABC):
    def __init__(self, id: int, user: User, restaurant: Restaurant, items: list[MenuItem], payment_strategy: IPaymentStrategy):
        self.id = id
        self.user = user
        self.restaurant = restaurant
        self.items = items
        self.payment_strategy = payment_strategy

    @abstractmethod
    def getType(self) -> str:
        pass