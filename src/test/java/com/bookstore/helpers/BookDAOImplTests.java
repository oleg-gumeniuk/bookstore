package test.java.com.bookstore.helpers;

import main.java.com.bookstore.controller.BookStoreController;
import main.java.com.bookstore.dao.impl.BookDAOImpl;
import main.java.com.bookstore.dao.impl.CartDAOImpl;
import main.java.com.bookstore.db.BookStoreDBManager;
import main.java.com.bookstore.helpers.BookHelper;
import main.java.com.bookstore.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-18
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public class BookDAOImplTests {

    private static final String BOOK_STORE_DATA = "bookstoredata.txt";

    public static BookStoreDBManager bookStoreDBHelper;
    public static BookDAOImpl bookDAOImpl;
    public static CartDAOImpl cartDAOImpl;

    @Before
    public void setUp() {

        //First we need to load the data from the provided txt file
        BookStoreController storeController = new BookStoreController();

        List<String> bookStoreData = storeController.loadFromFile(BOOK_STORE_DATA);

        //Initialize our fake DB
        bookStoreDBHelper = new BookStoreDBManager();
        bookDAOImpl = new BookDAOImpl(bookStoreDBHelper);
        cartDAOImpl = new CartDAOImpl(bookStoreDBHelper);


        //Parsing and storing data into our fake DB
        for(String bookStr: bookStoreData) {
            Book book = BookHelper.parseBook(bookStr);
            bookDAOImpl.insert(book);
        }
    }

    @After
    public void tearDown() {
        bookStoreDBHelper.deleteAll();
    }

    @Test
    public void testFindAllBooks() {
        List<Book> books = bookDAOImpl.findAllBooks();
        assertTrue(books.size() == 7);
    }

    @Test
    public void testSearchBook() {
        //test with a search query where 2 of the books is an expected result
        List<Book> books = bookDAOImpl.findBooks("generic");
        assertTrue(books.size() == 2);

        //test with a search query where all existing books is an expected result
        books = bookDAOImpl.findBooks("");
        assertTrue(books.size() == 7);

        //test with a search query where 0 is an expected result
        books = bookDAOImpl.findBooks("ZZZ");
        assertTrue(books.size() == 0);
    }


    @Test
    public void testAddToCart() {
    }
}
