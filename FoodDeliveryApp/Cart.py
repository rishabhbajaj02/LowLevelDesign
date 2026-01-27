from MenuItem import MenuItem
from Restaurant import Restaurant

class Cart:
    def __init__(self, restaurant: Restaurant, items: list[MenuItem]):
        self.restaurant = restaurant
        self.items = items

    def add_item(self, item: MenuItem):
        self.items.append(item)

    def remove_item(self, item: MenuItem):
        self.items.remove(item)

    def get_total(self) -> float:
        return sum(item.price for item in self.items)

    def isEmpty(self) -> bool:
        return len(self.items) == 0