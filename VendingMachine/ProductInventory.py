from Product import Product


class ProductInventory:
    def __init__(self, products: list[Product]):
        self.products = products

    def getProduct(self, code: str) -> Product | None:
        for product in self.products:
            if product.code == code:
                return product
        return None

    def isAvailable(self, code: str) -> bool:
        product = self.getProduct(code)
        return product is not None and product.quantity > 0
