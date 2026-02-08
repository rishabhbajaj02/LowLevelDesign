from FoodDeliveryApp.Order import Order

class OrderManager:

    _instance = None
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(OrderManager, cls).__new__(cls)
        return cls._instance    

    def __init__(self):
        if not hasattr(self, 'initialized'):
            self.orders = []
            self.initialized = True

    def add_order(self, order: Order):
        self.orders.append(order)

    def remove_order(self, order: Order):
        self.orders.remove(order)

    def list_orders(self) -> list[Order]:
        return self.orders

    def get_order(self, order_id: int) -> Order:
        for order in self.orders:
            if order.id == order_id:
                return order
        return None