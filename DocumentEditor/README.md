# Document Editor - Low Level Design

A flexible and extensible Document Editor system designed using SOLID principles and common design patterns.

## Class Structure

- **DocumentEditor**: The primary controller that manages the document and its persistence.
- **Document**: Represents the document's state, containing a collection of `DocumentElement`s.
- **DocumentElement (Interface)**: Abstract base class for all elements that can be part of a document.
  - **TextElement**: Represents a block of text.
  - **ImageElement**: Represents an image.
  - **NewLineElement**: Represents a line break.
  - **TabElement**: Represents a tab character.
- **Persistance (Interface)**: Abstract base class for different saving mechanisms.
  - **FilePersistance**: Implements saving to a local file.
  - **DBPersistance**: Implements saving to a database.

## SOLID Principles

- **Single Responsibility Principle (SRP)**:
  - `Document` is only responsible for holding elements and rendering them.
  - `DocumentEditor` manages the flow of user actions.
  - `Persistance` subclasses only handle data storage.
- **Open/Closed Principle (OCP)**:
  - New types of `DocumentElement` (e.g., Table, Link) can be added without modifying the `Document` class.
  - New storage methods (e.g., Cloud Storage) can be added by implementing the `Persistance` interface without changing the `DocumentEditor`.
- **Liskov Substitution Principle (LSP)**:
  - All `DocumentElement` subclasses can be used interchangeably in the `Document.elements` list.
  - All `Persistance` implementations can be substituted in `DocumentEditor` without affecting its logic.
- **Interface Segregation Principle (ISP)**:
  - The interfaces `DocumentElement` and `Persistance` are lean and focused on specific behaviors (`render` and `save` respectively).
- **Dependency Inversion Principle (DIP)**:
  - `DocumentEditor` depends on the `Persistance` abstraction rather than a concrete implementation like `FilePersistance`.

## Design Patterns Used

- **Strategy Pattern**: The `Persistance` mechanism is a strategy that can be swapped at runtime. `DocumentEditor` uses an instance of `Persistance` to save data.
- **Composite Pattern**: The `Document` acts as a composite that treats a single element or a list of elements the same way during rendering.
- **Bridge Pattern**: Decouples the `DocumentEditor` (abstraction) from the `Persistance` (implementation), allowing both to vary independently.
- **Flyweight Pattern**: (Potential) The `DocumentElement` structure allows for future optimization where common characters or styling could be shared across multiple elements to reduce memory footprint.
