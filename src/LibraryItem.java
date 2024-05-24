import java.util.*;
import java.time.*;

public class LibraryItem<T> {
    
    // Attributes of the LibraryItem
    private String title;
    private String author;
    private String itemID;
    private T category;
    private LocalDate releaseDate;
    private LocalDate dateAdded;

    /**
     * Constructor to create a new LibraryItem.
     * @param title The title of the item.
     * @param author The author of the item.
     * @param category The category of the item.
     * @param releaseDate The release date of the item.
     */
    public LibraryItem(String title, String author, T category, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.itemID = UUID.randomUUID().toString(); // Automatically generate unique ID
        this.category = category;
        this.releaseDate = releaseDate;
        this.dateAdded = LocalDate.now(); // Automatically set to the current date
    }

    // Getters

    /**
     * Gets the title of the item.
     * @return The title of the item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the author of the item.
     * @return The author of the item.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the unique ID of the item.
     * @return The item ID.
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Gets the category of the item.
     * @return The category of the item.
     */
    public T getCategory() {
        return category;
    }

    /**
     * Gets the release date of the item.
     * @return The release date of the item.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the date when the item was added to the catalog.
     * @return The date added to the catalog.
     */
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    // toString method for displaying the item details

    /**
     * Returns a string representation of the LibraryItem.
     * @return A string containing the item details.
     */
    @Override
    public String toString() {
        return "LibraryItem{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", itemID=" + itemID +
                ", category=" + category +
                ", releaseDate=" + releaseDate +
                ", dateAdded=" + dateAdded +
                '}';
    }
}