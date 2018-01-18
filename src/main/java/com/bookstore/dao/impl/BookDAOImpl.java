package main.java.com.bookstore.dao.impl;

import main.java.com.bookstore.dao.BookDAO;
import main.java.com.bookstore.db.BookStoreDBManager;
import main.java.com.bookstore.db.table.BookDB;
import main.java.com.bookstore.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */
public class BookDAOImpl implements BookDAO {

    private BookStoreDBManager dbHelper;

    public BookDAOImpl(BookStoreDBManager dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Inserts a book into a fake DB
     * @param book is a book to insert to
     * @return true if an operation was successful, false otherwise
     */
    @Override
    public boolean insert(Book book) {
        BookDB bookDB = new BookDB();

        try {
            bookDB.setTitle(book.getTitle());
            bookDB.setAuthor(book.getAuthor());
            bookDB.setPrice(book.getPrice());
            bookDB.setQuantity(book.getQuantity());

            dbHelper.insertBook(bookDB);

        } catch (RuntimeException ex) {
            return false;
        }

        return true;
    }

    /**
     * Gets a list of books from a fake DB
     * @return a list of books that exists in the DB
     */
    @Override
    public List<Book> findAllBooks() {
        String searchString = null;

        List<Book> result = findBooks(searchString);

        return result;
    }

    /**
     * Gets a list of books from a fake DB based on a search string
     * @param searchString is a search parameter used to filter out books by author or title
     * @return a filtered list of books
     */
    @Override
    public List<Book> findBooks(String searchString) {
        List<Book> result = new ArrayList<Book>();

        List<BookDB> books = dbHelper.findBooks(searchString);
        for (BookDB bookDB: books) {
            Book book = new Book();

            book.setId(bookDB.getId());
            book.setTitle(bookDB.getTitle());
            book.setTitle(bookDB.getTitle());
            book.setAuthor(bookDB.getAuthor());
            book.setPrice(bookDB.getPrice());
            book.setQuantity(bookDB.getQuantity());

            result.add(book);
        }

        return result;
    }
}
