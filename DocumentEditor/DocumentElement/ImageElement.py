from DocumentElement.DocumentElement import DocumentElement

class ImageElement(DocumentElement):
    def __init__(self, image_url: str):
        self.image_url = image_url

    def render(self) -> str:
        return f"<img src='{self.image_url}' />"