# Rate Limiting Strategies

This directory contains various implementations of rate-limiting algorithms, all following the `IRateLimitStrategy` interface.

## Algorithms Implemented

### 1. Token Bucket (`TokenBucketStrategy`)
- **Concept**: A bucket is filled with tokens at a constant `refill_rate`. Each request consumes a token. If the bucket is empty, the request is denied.
- **Key Features**: 
  - Supports **burstiness** (can handle a burst of requests up to the `capacity`).
  - Smooths out traffic over time.
- **Parameters**:
  - `capacity`: Maximum number of tokens the bucket can hold.
  - `refill_rate`: How many tokens are added per second.

### 2. Fixed Window Counter (`FixedWindowStrategy`)
- **Concept**: Time is divided into fixed intervals (windows). A counter is maintained for each window. Once the counter exceeds the `capacity`, further requests in that window are rejected.
- **Key Features**:
  - Very **memory efficient**.
  - Simple to implement.
  - Potential "boundary problem" where double the amount of traffic can sneak in at the edge of two windows.
- **Parameters**:
  - `capacity`: Maximum requests allowed per window.
  - `window_size`: The duration of the window in seconds.

## Structure

- **`IRateLimitStrategy.py`**: The abstract base class (interface). Every new strategy must implement the `allowRequest(key)` method.
- **`Bucket.py`**: A helper class for the Token Bucket algorithm to manage token state and refills.

## Adding a New Strategy
To add a new algorithm (e.g., Sliding Window Logs or Leaky Bucket):
1. Create a new file in this directory.
2. Inherit from `IRateLimitStrategy`.
3. Implement the `allowRequest` logic.
4. It can now be plugged into the `RateLimiter` without any changes to the core limiter logic.
