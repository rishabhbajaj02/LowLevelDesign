from Persistance.Persistance import Persistance

class FilePersistance(Persistance):
    def save(self, documentId: str, data: str) -> None:
        file_path = f"{documentId}.txt"
        with open(file_path, "w") as f:
            f.write(data)
