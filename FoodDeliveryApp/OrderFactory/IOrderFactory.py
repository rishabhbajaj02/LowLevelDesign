from FoodDeliveryApp.Cart import Cart
from abc import ABC, abstractmethod
from FoodDeliveryApp.Order import Order
from FoodDeliveryApp.User import User
from FoodDeliveryApp.PaymentStrategy.IPaymentStrategy import IPaymentStrategy

class IOrderFactory(ABC):
    @abstractmethod
    def createOrder(self, 
                    id: int, 
                    user: User, 
                    cart: Cart, 
                    payment_strategy: IPaymentStrategy, 
                    order_type: str) -> Order:
        pass