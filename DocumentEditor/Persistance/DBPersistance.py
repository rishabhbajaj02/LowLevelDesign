from Persistance.Persistance import Persistance

class DBPersistance(Persistance):
    def save(self, documentId: str, data: str) -> None:
        print(f"Saving document to database: {documentId}")