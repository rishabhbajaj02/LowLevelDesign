from DocumentElement.DocumentElement import DocumentElement

class TabElement(DocumentElement):
    def __init__(self):
        pass

    def render(self) -> str:
        return f"\t"