from FoodDeliveryApp.Tomato import Tomato
from FoodDeliveryApp.User import User
from FoodDeliveryApp.Cart import Cart
from FoodDeliveryApp.PaymentStrategy.UPIPayment import UPIPayment
from FoodDeliveryApp.OrderFactory.NowOrderFactory import NowOrderFactory
from FoodDeliveryApp.OrderFactory.ScheduleOrderFactory import ScheduleOrderFactory
from FoodDeliveryApp.PaymentStrategy.CreditCardPayment import CreditCardPayment

def main():
    # 1. Initialize Tomato
    tomato = Tomato()
    
    # 2. Seed restaurants
    print("Seeding restaurants...")
    tomato.seed_restaurants()
    
    # 3. Create a user
    print("Creating user...")
    user_cart = Cart(None, [])
    user = User(1, "John Doe", "123 Main St", user_cart)
    
    # 4. Search for restaurants by location
    print("Searching for restaurants in 'Location 1'...")
    restaurants = tomato.searchByLocation("Location 1")
    for r in restaurants:
        print(f"Found: {r.name} at {r.loc}")
    
    if not restaurants:
        print("No restaurants found!")
        return

    selected_restaurant = restaurants[0]
    print(f"Selecting restaurant: {selected_restaurant.name}")
    tomato.selectRestaurant(user, selected_restaurant)
    
    # 5. Add items to cart
    print("Adding items to cart...")
    if selected_restaurant.menu_items:
        item = selected_restaurant.menu_items[0]
        print(f"Adding {item.name} - ${item.price}")
        tomato.addToCart(user, item)
        
        if len(selected_restaurant.menu_items) > 1:
            item2 = selected_restaurant.menu_items[1]
            print(f"Adding {item2.name} - ${item2.price}")
            tomato.addToCart(user, item2)
    
    # 6. Get cart total
    total = tomato.getCartTotal(user)
    print(f"Cart total: ${total}")
    
    # 7. Checkout (Standard Delivery)
    print("Checking out with NowOrderFactory (DeliveryOrder)...")
    payment_strategy = UPIPayment()
    order_factory = NowOrderFactory()
    order = tomato.checkout(user, payment_strategy, order_factory, "DeliveryOrder")
    
    # 8. Simulate payment
    payment_strategy.pay(total)
    
    # 9. Print order summary
    print("\n--- Order 1 Summary ---")
    print(f"Order ID: {order.id}")
    print(f"User: {order.user.name}")
    print(f"Restaurant: {order.restaurant.name}")
    print(f"Order Type: {order.getType()}")
    print("Items:")
    for item in order.items:
        print(f" - {item.name}: ${item.price}")
    print(f"Total: ${total}")
    print("----------------------")

    # 10. Simulate another order (Scheduled Pickup)
    print("\nSimulating another order (Scheduled Pickup)...")
    scheduled_factory = ScheduleOrderFactory()
    p_strategy = CreditCardPayment()
    order2 = tomato.checkout(user, p_strategy, scheduled_factory, "PickupOrder")
    
    p_strategy.pay(total)
    
    print("\n--- Order 2 Summary ---")
    print(f"Order ID: {order2.id}")
    print(f"Order Type: {order2.getType()}")
    print(f"Restaurant: {order2.restaurant.name}")
    print("----------------------")

if __name__ == "__main__":
    main()
