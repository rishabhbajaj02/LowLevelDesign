from Observer.IVendingMachineObserver import IVendingMachineObserver


class InventoryAlertObserver(IVendingMachineObserver):
    def update(self, event: str, data: dict) -> None:
        if event == "LOW_INVENTORY":
            print(f"[ALERT] Low inventory for {data['product']}: {data['remaining']} remaining")
