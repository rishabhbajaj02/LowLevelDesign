from DocumentElement.DocumentElement import DocumentElement
class Document:
    def __init__(self, id: str, elements: list[DocumentElement]):
        self.id = id
        self.elements = elements

    def render(self) -> str:
        return "".join([element.render() for element in self.elements   ])