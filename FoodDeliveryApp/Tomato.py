from FoodDeliveryApp.OrderManager import OrderManager
from FoodDeliveryApp.PaymentStrategy.IPaymentStrategy import IPaymentStrategy
from FoodDeliveryApp.User import User
from FoodDeliveryApp.MenuItem import MenuItem
from FoodDeliveryApp.RestaurantManager import RestaurantManager
from FoodDeliveryApp.Restaurant import Restaurant
from FoodDeliveryApp.OrderFactory.IOrderFactory import IOrderFactory
from FoodDeliveryApp.OrderFactory.NowOrderFactory import NowOrderFactory
from FoodDeliveryApp.Cart import Cart
from FoodDeliveryApp.Order import Order
class Tomato:
    def __init__(self):
        pass

    def seed_restaurants(self):
        restaurant1 = Restaurant(1, "Restaurant 1", "Location 1", [MenuItem("Burger", "B01", 10), MenuItem("Fries", "F01", 5)])
        restaurant2 = Restaurant(2, "Restaurant 2", "Location 2", [MenuItem("Pizza", "P01", 15), MenuItem("Pasta", "P02", 12)])
        restaurant3 = Restaurant(3, "Restaurant 3", "Location 3", [MenuItem("Sushi", "S01", 20), MenuItem("Ramen", "R01", 18)])
        
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

    def checkout(self, user: User, payment_strategy: IPaymentStrategy, factory: IOrderFactory, order_type: str):
        cart = user.cart
        orderManager = OrderManager()
        order_id = len(orderManager.list_orders()) + 1
        order = factory.createOrder(order_id, user, cart, payment_strategy, order_type)
        orderManager.add_order(order)
        return order
