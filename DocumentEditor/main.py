from DocumentEditor import DocumentEditor
from Persistance.FilePersistance import FilePersistance
from Document import Document

def main():
    persistance = FilePersistance()
    document = Document("1", [])
    editor = DocumentEditor(persistance, document)
    editor.addText("Hello World")
    editor.addTab()
    editor.addText("Hello World")
    editor.addNewLine()
    editor.addText("Hello World")
    editor.addTab()
    editor.addTab()
    editor.addTab()
    editor.addTab()
    editor.addTab()
    editor.addText("Hello World")
    editor.addNewLine()
    editor.addImage("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png")
    editor.addTab()
    editor.addText("Hello World")
    editor.addNewLine()
    editor.addText("Hello World")
    editor.addTab()
    editor.addText("Hello World")
    editor.addNewLine()
    editor.addImage("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png")
    editor.save()

if __name__ == "__main__":
    main()