from DocumentElement.DocumentElement import DocumentElement

class NewLineElement(DocumentElement):
    def __init__(self):
        pass

    def render(self) -> str:
        return f"\n"