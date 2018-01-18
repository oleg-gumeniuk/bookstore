package main.java.com.bookstore.db;

import main.java.com.bookstore.db.table.BookDB;
import main.java.com.bookstore.db.table.CartDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * This class is just an emulation of a corresponding Book MYSQL DB
 *
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class BookStoreDB {

    /**
     * A list of books that is stored in our fake DB
     */
    private List<BookDB> books;

    /**
     * Let's assume that for this assignment we don't need to support more than 1 shopping cart
     */
    private CartDB cart;

    public BookStoreDB() {
        this.books = new ArrayList<BookDB>();
        this.cart = new CartDB();
    }

    /**
     * Saving a new book into our fake table
     * @param bookDB a new book to be saved
     */
    protected void insertBook(BookDB bookDB) {
        books.add(bookDB);
    }

    /**
     * Returns all books from the fake table
     * @return
     */
    protected List<BookDB> getBooks() {
        return books;
    }

    /**
     * Finds books by title or author
     * @return
     */
    protected List<BookDB> findBooks(String searchString) {
        List<BookDB> result = new ArrayList<BookDB>();

        if (searchString != null && !searchString.isEmpty()) {
            for(BookDB book: books) {
                if (book.getAuthor().toLowerCase().contains(searchString.toLowerCase()) ||
                        book.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
                    result.add(book);
                }
            }
        } else {
            result.addAll(books);
            return result;
        }

        return result;
    }

    /**
     * Finds a book by Id
     * @param id
     * @return
     */
    public BookDB findBookById(UUID id) {
        BookDB bookDB = null;

        for (BookDB book : books) {
            if (book.getId().equals(id)) {
                bookDB = book;
                break;
            }
        }

        return bookDB;
    }


    /**
     * Returns a content of user cart
     * @return
     */
    public Map<UUID, Integer> getCart() {
        return cart.getBooks();
    }

    public void addToCart(UUID bookID) {
        cart.addBook(bookID);
    }

    public void removeFromCart(UUID bookID) {
        cart.removeBook(bookID);
    }

    public void deleteAll() {
        books.clear();
    }

    public void clearCart() {
        cart.clear();
    }
}
