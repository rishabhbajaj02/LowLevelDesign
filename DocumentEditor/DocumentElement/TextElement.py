from DocumentElement.DocumentElement import DocumentElement

class TextElement(DocumentElement):
    def __init__(self, text: str):
        self.text = text

    def render(self) -> str:
        return f"{self.text}"