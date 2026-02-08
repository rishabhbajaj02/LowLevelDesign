from FoodDeliveryApp.Order import Order
from FoodDeliveryApp.User import User
from FoodDeliveryApp.Restaurant import Restaurant
from FoodDeliveryApp.MenuItem import MenuItem
from FoodDeliveryApp.PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class PickupOrder(Order):
    def __init__(self, id: int, user: User, restaurant: Restaurant, items: list[MenuItem], payment_strategy: IPaymentStrategy):
        super().__init__(id, user, restaurant, items, payment_strategy)

    def getType(self) -> str:   
        return "PickupOrder"