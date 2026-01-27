from FoodDeliveryApp.User import User
from FoodDeliveryApp.MenuItem import MenuItem
from FoodDeliveryApp.RestaurantManager import RestaurantManager
from FoodDeliveryApp.Restaurant import Restaurant
class Tomato:
    def __init__(self):
        pass

    def seed_restaurants(self):
        restaurant1 = Restaurant(1, "Restaurant 1", "Location 1", [MenuItem(1, "Item 1", 10), MenuItem(2, "Item 2", 20)])
        restaurant2 = Restaurant(2, "Restaurant 2", "Location 2", [MenuItem(3, "Item 3", 30), MenuItem(4, "Item 4", 40)])
        restaurant3 = Restaurant(3, "Restaurant 3", "Location 3", [MenuItem(5, "Item 5", 50), MenuItem(6, "Item 6", 60)])
        
        restaurantManager = RestaurantManager()
        restaurantManager.add_restaurant(restaurant1)
        restaurantManager.add_restaurant(restaurant2)
        restaurantManager.add_restaurant(restaurant3)


    def searchByLocation(self, location: str):
        restaurantManager = RestaurantManager()
        return restaurantManager.searchByLocation(location)

    def searchByName(self, name: str):
        restaurantManager = RestaurantManager()
        return restaurantManager.searchByName(name)

    def selectRestaurant(self, user: User, restaurant: Restaurant):
        cart = user.cart
        cart.restaurant = restaurant

    def addToCart(self, user: User, item: MenuItem):
        cart = user.cart
        cart.add_item(item)

    def removeFromCart(self, user: User, item: MenuItem):
        cart = user.cart
        cart.remove_item(item)

    def getCartTotal(self, user: User) -> float:
        cart = user.cart
        return cart.get_total()

    def checkout(self, user: User, payment_strategy: IPaymentStrategy):
        cart = user.cart
        order = Order(1, user, cart.restaurant, cart.items, payment_strategy)
        orderManager = OrderManager()
        orderManager.add_order(order)
        return order
