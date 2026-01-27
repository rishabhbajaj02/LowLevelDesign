from FoodDeliveryApp.Cart import Cart
class User:
    def __init__(self, id: int, name: str, address: str, cart: Cart):
        self.id = id
        self.name = name
        self.address = address
        self.cart = cart