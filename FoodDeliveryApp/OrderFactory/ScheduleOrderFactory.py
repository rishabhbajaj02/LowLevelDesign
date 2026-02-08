from FoodDeliveryApp.OrderFactory.IOrderFactory import IOrderFactory
from FoodDeliveryApp.OrderTypes.DeliveryOrder import DeliveryOrder
from FoodDeliveryApp.OrderTypes.PickupOrder import PickupOrder
from FoodDeliveryApp.User import User
from FoodDeliveryApp.Cart import Cart
from FoodDeliveryApp.PaymentStrategy.IPaymentStrategy import IPaymentStrategy
from FoodDeliveryApp.Order import Order

class ScheduleOrderFactory(IOrderFactory):
    def createOrder(self, id: int, user: User, cart: Cart, payment_strategy: IPaymentStrategy, order_type: str) -> Order:
        if order_type == "DeliveryOrder":
            return DeliveryOrder(id, user, cart.restaurant, cart.items, payment_strategy)
        elif order_type == "PickupOrder":
            return PickupOrder(id, user, cart.restaurant, cart.items, payment_strategy)
        else:
            raise ValueError("Invalid order type")