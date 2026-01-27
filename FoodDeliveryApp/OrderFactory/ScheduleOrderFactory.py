from OrderFactory.IOrderFactory import IOrderFactory
from OrderTypes.DeliveryOrder import DeliveryOrder
from OrderTypes.PickupOrder import PickupOrder
from User import User
from Cart import Cart
from PaymentStrategy.IPaymentStrategy import IPaymentStrategy
from Order import Order

class ScheduleOrderFactory(IOrderFactory):
    def createOrder(self, id: int, user: User, cart: Cart, payment_strategy: IPaymentStrategy, order_type: str) -> Order:
        if order_type == "DeliveryOrder":
            return DeliveryOrder(id, user, cart.restaurant, cart.items, payment_strategy)
        elif order_type == "PickupOrder":
            return PickupOrder(id, user, cart.restaurant, cart.items, payment_strategy)
        else:
            raise ValueError("Invalid order type")