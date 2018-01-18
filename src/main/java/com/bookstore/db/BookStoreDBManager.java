package main.java.com.bookstore.db;

import main.java.com.bookstore.db.table.BookDB;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class BookStoreDBManager {

    BookStoreDB bookStoreDB;

    public BookStoreDBManager() {
        this.bookStoreDB = new BookStoreDB();
    }

    public void insertBook(BookDB bookDB) {
        bookStoreDB.insertBook(bookDB);
    }

    public List<BookDB> getBooks() {
        return bookStoreDB.getBooks();
    }

    public List<BookDB> findBooks(String searchString) {
        return bookStoreDB.findBooks(searchString);
    }

    public BookDB findBookById(UUID id) {
        return bookStoreDB.findBookById(id);
    }

    /**
     * Returns the content of the shopping cart
     */
    public Map<UUID, Integer> getCart() {
        return bookStoreDB.getCart();
    }

    /**
     * Adds a new book to the cart
     */
    public void addToCart(UUID bookID) {
        bookStoreDB.addToCart(bookID);
    }

    /**
     * Removes a book from the cart
     */
    public void removeFromCart(UUID bookID) {
        bookStoreDB.removeFromCart(bookID);
    }

    public void deleteAll() {
        bookStoreDB.deleteAll();
    }

    public void clearCart() {
        bookStoreDB.clearCart();
    }
}
