from FoodDeliveryApp.Restaurant import Restaurant

class RestaurantManager:
    _instance = None
    
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super(RestaurantManager, cls).__new__(cls)
        return cls._instance

        
    def __init__(self):
        if not hasattr(self, 'initialized'):
            self.restaurants = []
            self.initialized = True

    def add_restaurant(self, restaurant: Restaurant):
        self.restaurants.append(restaurant)

    def remove_restaurant(self, restaurant: Restaurant):
        self.restaurants.remove(restaurant)

    def get_restaurant(self, restaurant_id: int) -> Restaurant:
        for restaurant in self.restaurants:
            if restaurant.id == restaurant_id:
                return restaurant
        return None

    def list_restaurants(self) -> list[Restaurant]:
        return self.restaurants

    def searchByLocation(self, location: str) -> list[Restaurant]:
        return [restaurant for restaurant in self.restaurants if restaurant.loc == location]

    def searchByName(self, name: str) -> list[Restaurant]:
        return [restaurant for restaurant in self.restaurants if restaurant.name == name]