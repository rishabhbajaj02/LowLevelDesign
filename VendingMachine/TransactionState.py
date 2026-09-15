from enum import Enum


class TransactionState(Enum):
    NOT_STARTED = "NOT_STARTED"
    IN_PROGRESS = "IN_PROGRESS"
    CANCELLED = "CANCELLED"
    COMPLETED = "COMPLETED"
    OUT_OF_STOCK = "OUT_OF_STOCK"
