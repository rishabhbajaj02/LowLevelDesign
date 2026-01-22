from Persistance import Persistance
from Document import Document
from DocumentElement.TextElement import TextElement
from DocumentElement.ImageElement import ImageElement
from DocumentElement.NewLineElement import NewLineElement
from DocumentElement.TabElement import TabElement

class DocumentEditor:
    def __init__(self, persistance: Persistance, document: Document):
        self.persistance = persistance
        self.document = document

    def addText(self, text: str) -> None:
        self.document.elements.append(TextElement(text))

    def addImage(self, image_url: str) -> None:
        self.document.elements.append(ImageElement(image_url))

    def addNewLine(self) -> None:
        self.document.elements.append(NewLineElement())

    def addTab(self) -> None:
        self.document.elements.append(TabElement())

    def save(self) -> None:
        self.persistance.save(self.document.id, self.document.render())