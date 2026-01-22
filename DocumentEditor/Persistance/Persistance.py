from abc import ABC, abstractmethod

class Persistance(ABC):
    @abstractmethod
    def save(self, document: Document) -> None:
        pass