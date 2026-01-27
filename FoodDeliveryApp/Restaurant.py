from MenuItem import MenuItem

class Restaurant:
    def __init__(self, id: int, name: str, loc: str, menu_items: list[MenuItem]):
        self.id = id
        self.name = name
        self.loc = loc
        self.menu_items = menu_items