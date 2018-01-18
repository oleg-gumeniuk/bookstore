package main.java.com.bookstore.db.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-16
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class CartDB {

    private Map<UUID, Integer> bookUUIDs = new LinkedHashMap<UUID, Integer>();

    /**
     * Adds a new book to the cart
     * @param bookID
     * @return
     */
    public void addBook(UUID bookID) {
        Integer booksCount = 0;

        if (!bookUUIDs.containsKey(bookID)) {
            bookUUIDs.put(bookID, booksCount);
        }

        booksCount = bookUUIDs.get(bookID);
        bookUUIDs.put(bookID, ++booksCount);
    }

    /**
     * Removes a book from the cart
     * @param bookID
     */
    public void removeBook(UUID bookID) {
        if (bookUUIDs.containsKey(bookID)) {
            Integer booksCount = bookUUIDs.get(bookID);
            if (booksCount > 1) {
                bookUUIDs.put(bookID, --booksCount);
            } else {
                bookUUIDs.remove(bookID);
            }
        }
    }

    /**
     * Returns the content of the cart
     * @return
     */
    public Map<UUID, Integer> getBooks() {
        return bookUUIDs;
    }

    /**
     * Removes all books from the cart
     */
    public void clear() {
        bookUUIDs.clear();
    }
}
