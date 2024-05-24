import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

// Import validators package
import validators.*;

public class App {
    // Catalog to store library items, with String as the type for category handling
    private static Catalog<String> catalog = new Catalog<>();
    // Scanner object for reading user input
    private static final Scanner scanner = new Scanner(System.in);
    // Formatter for parsing dates in the format YYYY-MM-DD
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void main(String[] args) {
        boolean exit = false;

        // Main loop to handle user actions
        while (!exit) {
            System.out.println("Choose an action: (1) Add Item, (2) Remove Item, (3) Display Catalog, (4) Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            // Handle user's choice
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    displayCatalogOptions();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Adds a new item to the catalog by collecting details from the user.
     */
    private static void addItem() {
        // Use InputValidator to check that the strings are not null and not empty
        String title = InputValidator.getInput(scanner, "Enter title:", 
                                               InputValidator::validateNonNullAndNonEmpty, 
                                               "Title must not be empty.");
        String author = InputValidator.getInput(scanner, "Enter author:", 
                                                InputValidator::validateNonNullAndNonEmpty, 
                                                "Author must not be empty.");
        String category = InputValidator.getInput(scanner, "Enter category:", 
                                                  InputValidator::validateNonNullAndNonEmpty, 
                                                  "Category must not be empty.");
        // For the date, use the getParsedInput to validate and parse the date
        LocalDate releaseDate = InputValidator.getParsedInput(scanner, "Enter release date (YYYY-MM-DD):",
                                                              s -> InputValidator.validateAndParseDate(s, formatter),
                                                              "Invalid date format. Please use YYYY-MM-DD.");
        
        // Create a new LibraryItem and add it to the catalog
        LibraryItem<String> newItem = new LibraryItem<>(title, author, category, releaseDate);
        catalog.addItem(newItem);
    }

    /**
     * Removes an item from the catalog by collecting the item ID from the user.
     */
    private static void removeItem() {
        String removeID = InputValidator.getInput(scanner, "Enter item ID to remove:",
                                                  input -> InputValidator.validateNonNullAndNonEmpty(input) && catalog.hasItem(input),
                                                  "Item ID does not exist or is invalid. Please enter a valid ID.");
        catalog.removeItem(removeID);
        System.out.println("Item removed successfully.");
    }

    /**
     * Displays catalog options to the user, either by category or displaying all items.
     */
    private static void displayCatalogOptions() {
        String response = InputValidator.getInput(scanner, "Do you want to view by category? (yes/no)",
                                                  input -> input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no"),
                                                  "Please answer 'yes' or 'no'.");
        if (response.equalsIgnoreCase("yes")) {
            displayCategories();
        } else {
            catalog.displayCatalog(Optional.empty());
        }
    }

    /**
     * Displays categories to the user and allows selection of a category to filter the catalog display.
     */
    private static void displayCategories() {
        Map<Integer, String> categoryMap = listCategories();
        if (!categoryMap.isEmpty()) {
            System.out.println("Categories:");
            categoryMap.forEach((index, category) -> System.out.println(index + ": " + category));

            int categoryChoice = InputValidator.getParsedInput(scanner, "Enter the number for the category:",
                                                               input -> {
                                                                   int choice = Integer.parseInt(input);
                                                                   if (categoryMap.containsKey(choice)) {
                                                                       return choice;
                                                                   } else {
                                                                       throw new IllegalArgumentException("Invalid category number");
                                                                   }
                                                               },
                                                               "Invalid category number. Please enter a valid number.");
            catalog.displayCatalog(Optional.of(categoryMap.get(categoryChoice)));
        } else {
            System.out.println("No categories available.");
        }
    }

    /**
     * Lists all categories in the catalog and returns a map of category indices to category names.
     * @return A map of category indices to category names.
     */
    private static Map<Integer, String> listCategories() {
        Set<String> categories = catalog.getCategories();
        int index = 1;
        Map<Integer, String> categoryMap = new HashMap<>();
        for (String category : categories) {
            categoryMap.put(index, category);
            index++;
        }
        return categoryMap;
    }
}
