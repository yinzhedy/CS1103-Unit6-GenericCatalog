# Generic Library Catalog

This project is a Java application for managing a library catalog. It allows users to add, remove, and display various types of library items such as books and DVDs. The application uses generics to ensure flexibility and reusability of the code.

## Features

- Add new library items to the catalog.
- Remove existing items from the catalog.
- Display all items in the catalog or filter by category.
- Input validation to ensure data integrity.

## Classes

### `App`

This is the main class that provides the command-line interface for users to interact with the library catalog.

- **Methods**:
  - `main(String[] args)`: The entry point of the application. Handles user actions.
  - `addItem()`: Collects details from the user to add a new item to the catalog.
  - `removeItem()`: Collects the item ID from the user to remove an item from the catalog.
  - `displayCatalogOptions()`: Provides options to the user to view the catalog by category or view all items.
  - `displayCategories()`: Displays the list of categories to the user and allows selection.
  - `listCategories()`: Retrieves and lists all unique categories in the catalog.

### `InputValidator`

This class provides methods for input validation and parsing.

- **Methods**:
  - `getInput(Scanner scanner, String prompt, Predicate<String> validator, String errorMessage)`: Generic method to get and validate user input.
  - `getParsedInput(Scanner scanner, String prompt, Function<String, T> parser, String errorMessage)`: Method to get and parse user input into another type.
  - `validateAndParseDate(String dateInput, DateTimeFormatter formatter)`: Validates and parses a date string.
  - `validateNonNullAndNonEmpty(T input)`: Validates that input is non-null and non-empty.
  - `validateUniqueness(U value, Collection<T> collection, Function<T, U> extractor)`: Ensures input is unique in a collection.
  - `includeAlphabets(T input, boolean shouldInclude)`: Validates presence or absence of alphabetical characters.
  - `includeSpecialCharacters(T input, boolean shouldInclude)`: Validates presence or absence of special characters.
  - `includeNumbers(T input, boolean shouldInclude)`: Validates presence or absence of numbers.
  - `validateDate(String dateInput, boolean mustBePast, boolean mustBeFuture, DateTimeFormatter... formatters)`: Validates a date string.
  - `validateDate(LocalDate date, boolean mustBePast, boolean mustBeFuture)`: Validates a LocalDate object.

### `LibraryItem<T>`

This class represents a library item and includes attributes such as title, author, item ID, category, release date, and date added.

- **Attributes**:
  - `String title`: The title of the item.
  - `String author`: The author of the item.
  - `String itemID`: A unique identifier for the item.
  - `T category`: The category of the item.
  - `LocalDate releaseDate`: The release date of the item.
  - `LocalDate dateAdded`: The date the item was added to the catalog.

- **Methods**:
  - `LibraryItem(String title, String author, T category, LocalDate releaseDate)`: Constructor to create a new library item.
  - `getTitle()`, `getAuthor()`, `getItemID()`, `getCategory()`, `getReleaseDate()`, `getDateAdded()`: Getters for the attributes.
  - `toString()`: Returns a string representation of the library item.

### `Catalog<T>`

This class represents the catalog which stores library items. It uses a map to store items by their ID.

- **Methods**:
  - `addItem(LibraryItem<T> item)`: Adds a new item to the catalog.
  - `removeItem(String itemID)`: Removes an item from the catalog.
  - `getItem(String itemID)`: Retrieves an item from the catalog.
  - `getCategories()`: Retrieves all unique categories in the catalog.
  - `hasItems()`: Checks if there are any items in the catalog.
  - `hasItem(String itemID)`: Checks if a specific item exists in the catalog.
  - `displayCatalog(Optional<T> category)`: Displays the catalog items, optionally filtered by category.

## How to Run

1. Clone the repository to your local machine.
2. Open the project in your IDE.
3. Run the `App` class to start the application.
4. Follow the on-screen prompts to add, remove, or view items in the catalog.

