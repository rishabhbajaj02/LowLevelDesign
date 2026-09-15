from Observer.IVendingMachineObserver import IVendingMachineObserver


class TransactionLogObserver(IVendingMachineObserver):
    def __init__(self):
        self.logs: list[str] = []

    def update(self, event: str, data: dict) -> None:
        log_entry = f"[LOG] {event}: {data}"
        self.logs.append(log_entry)
        print(log_entry)
