from abc import ABC, abstractmethod

class DocumentElement(ABC):
    @abstractmethod
    def render(self) -> str:
        pass