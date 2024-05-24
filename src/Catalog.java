import java.util.*;

public class Catalog<T> {
    // A map to store library items, with the item ID as the key and the LibraryItem as the value
    private Map<String, LibraryItem<T>> itemsCatalog = new HashMap<>();

    /**
     * Adds a new item to the catalog.
     * @param item The LibraryItem to be added.
     */
    public void addItem(LibraryItem<T> item) {
        itemsCatalog.put(item.getItemID(), item);
    }

    /**
     * Removes an item from the catalog.
     * @param itemID The ID of the item to be removed.
     * @throws IllegalArgumentException if the item ID does not exist in the catalog.
     */
    public void removeItem(String itemID) {
        if (!itemsCatalog.containsKey(itemID)) {
            throw new IllegalArgumentException("Item with ID " + itemID + " does not exist.");
        }
        itemsCatalog.remove(itemID);
    }

    /**
     * Retrieves an item from the catalog.
     * @param itemID The ID of the item to retrieve.
     * @return The LibraryItem with the specified ID, or null if it does not exist.
     */
    public LibraryItem<T> getItem(String itemID) {
        return itemsCatalog.get(itemID);
    }

    /**
     * Retrieves all unique categories of items in the catalog.
     * @return A set of categories present in the catalog.
     */
    public Set<T> getCategories() {
        Set<T> categories = new HashSet<>();
        for (LibraryItem<T> item : itemsCatalog.values()) {
            categories.add(item.getCategory());
        }
        return categories;
    }

    /**
     * Checks if there are any items in the catalog.
     * @return true if the catalog is not empty, false otherwise.
     */
    public boolean hasItems() {
        return !itemsCatalog.isEmpty();
    }

    /**
     * Checks if a specific item exists in the catalog.
     * @param itemID The ID of the item to check.
     * @return true if the item exists in the catalog, false otherwise.
     */
    public boolean hasItem(String itemID) {
        return itemsCatalog.containsKey(itemID);
    }

    /**
     * Displays the catalog items, optionally filtered by category.
     * @param category An Optional containing the category to filter by. If empty, all items are displayed.
     */
    public void displayCatalog(Optional<T> category) {
        // Print the table headers
        System.out.printf("+-------------------+-----------------------+-----------------------+-------------------+--------------------------------------+-------------------+%n");
        System.out.printf("| %-17s | %-21s | %-21s | %-17s | %-36s | %-17s |%n", "Category", "Title", "Author", "Release Date", "Item ID", "Date Added");
        System.out.printf("+-------------------+-----------------------+-----------------------+-------------------+--------------------------------------+-------------------+%n");

        // Stream through the catalog items
        itemsCatalog.values().stream()
            // Filter items by category if a category is provided
            .filter(item -> category.isEmpty() || item.getCategory().equals(category.get()))
            .forEach(item -> {
                // Print each item's details in a formatted row
                System.out.printf("| %-17s | %-21s | %-21s | %-17s | %-36s | %-17s |%n",
                    item.getCategory(), item.getTitle(), item.getAuthor(), item.getReleaseDate(), item.getItemID(), item.getDateAdded());
                System.out.printf("+-------------------+-----------------------+-----------------------+-------------------+--------------------------------------+-------------------+%n");
            });
    }
}
