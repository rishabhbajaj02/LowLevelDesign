# Rate Limiter - Low Level Design

A robust, strategy-based Rate Limiter system designed to demonstrate flexible algorithm selection.

## Class Structure

- **RateLimiter**: The context class that clients interact with. It delegates the rate-limiting decision to a strategy.
- **IRateLimitStrategy (Interface)**: The common interface for all rate-limiting algorithms.
  - **TokenBucketStrategy**: Implements the Token Bucket algorithm (good for handling bursts).
  - **FixedWindowStrategy**: Implements the Fixed Window Counter algorithm (simple, time-slice based).
- **Bucket**: (Conceptual/Utility) Manages the state for the Token Bucket algorithm.

## SOLID Principles

- **Single Responsibility Principle (SRP)**:
  - `RateLimiter` only coordinates the check.
  - Each Strategy class only contains the logic for its specific algorithm.
- **Open/Closed Principle (OCP)**:
  - New algorithms (e.g., Sliding Window, Leaky Bucket) can be added by creating a new class implementing `IRateLimitStrategy` without modifying the `RateLimiter` or existing strategies.
- **Dependency Inversion Principle (DIP)**:
  - `RateLimiter` depends on the `IRateLimitStrategy` abstraction, not the concrete implementations like `TokenBucketStrategy`.

## Design Patterns Used

- **Strategy Pattern**: The core of this system. It allows the `RateLimiter` to switch between different algorithms at runtime by injecting a different strategy instance.
- **Dependency Injection**: The strategy is provided to the `RateLimiter` during initialization, making the system highly testable and configurable.
