package main.java.com.bookstore.dao;

import main.java.com.bookstore.model.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public interface BookDAO {

    /**
     * Inserts a new book into a fake DB
     * @param book is a newly created book model
     * @return true if an operation is success, false otherwise
     */
    public boolean insert(Book book);

    /**
     * Searches for the books where an author or a title mathes the search string
     * @param searchString is a string to search books by
     * @return a list of filtered out books
     */
    public List<Book> findBooks(String searchString);

    /**
     * Returns a list of all books that are available
     * @return
     */
    public List<Book> findAllBooks();
}
