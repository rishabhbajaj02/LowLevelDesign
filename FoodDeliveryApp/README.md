# Food Delivery App (Tomato) - Low Level Design

A scalable and robust Food Delivery Application system (inspired by apps like Zomato/Swiggy) designed using SOLID principles and core design patterns.

## Class Structure

- **Tomato (Facade)**: The main entry point that simplifies complex operations into a single interface for the user.
- **Managers (Singletons)**:
  - **RestaurantManager**: Manages the catalog of restaurants and provides search functionality.
  - **OrderManager**: Tracks and manages all orders in the system.
- **Models**:
  - **User**: Represents a system user with a profile and a cart.
  - **Restaurant**: Represents a dining establishment with a menu.
  - **MenuItem**: Represents an individual food item.
  - **Cart**: Manages items selected by the user before checkout.
  - **Order**: Represents a confirmed transaction.
- **OrderFactory (Factory Pattern)**:
  - **IOrderFactory**: Interface for creating orders.
  - **NowOrderFactory**: Creates immediate orders (Delivery/Pickup).
  - **ScheduleOrderFactory**: Creates scheduled orders.
- **PaymentStrategy (Strategy Pattern)**:
  - **IPaymentStrategy**: Interface for payment processing.
  - **CreditCardPayment, UPIPayment, NetBankingPayment**: Concrete implementations of payment methods.
- **OrderTypes**:
  - **DeliveryOrder, PickupOrder**: Specialized order types with distinct behaviors.

## SOLID Principles

- **Single Responsibility Principle (SRP)**:
  - `Cart` only handles item management.
  - `RestaurantManager` only handles restaurant discovery.
  - `PaymentStrategy` only handles transaction logic.
- **Open/Closed Principle (OCP)**:
  - New payment methods (e.g., PayPal) can be added by implementing `IPaymentStrategy` without changing the checkout logic.
  - New order types or creation logic can be added via new Factories without modifying `Tomato`.
- **Liskov Substitution Principle (LSP)**:
  - `DeliveryOrder` and `PickupOrder` can be used wherever an `Order` is expected.
  - All payment strategies follow the `IPaymentStrategy` contract.
- **Interface Segregation Principle (ISP)**:
  - Interfaces like `IOrderFactory` and `IPaymentStrategy` are specific to their domains.
- **Dependency Inversion Principle (DIP)**:
  - `Tomato` (high-level) depends on `IOrderFactory` and `IPaymentStrategy` (abstractions) rather than concrete classes.

## Design Patterns Used

- **Facade Pattern**: The `Tomato` class hides the complexity of searching, cart management, and ordering from the client.
- **Singleton Pattern**: `RestaurantManager` and `OrderManager` ensure a single source of truth for restaurant and order data across the application.
- **Factory Method Pattern**: `IOrderFactory` and its implementations encapsulate the instantiation logic for different types of orders.
- **Strategy Pattern**: `PaymentStrategy` allows the application to select different payment algorithms at runtime.
- **Template Method**: The `Order` base class and its subclasses (`DeliveryOrder`, `PickupOrder`) allow for shared logic while specializing specific behaviors.
