package test.java.com.bookstore.helpers;

import main.java.com.bookstore.controller.BookStoreController;
import main.java.com.bookstore.dao.impl.BookDAOImpl;
import main.java.com.bookstore.dao.impl.CartDAOImpl;
import main.java.com.bookstore.db.BookStoreDBManager;
import main.java.com.bookstore.db.table.BookDB;
import main.java.com.bookstore.helpers.BookHelper;
import main.java.com.bookstore.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-18
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class CartDAOImplTests {

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
    public void testAddToCart() {
        //attempting to add a book that doesn't exist
        UUID uuid = UUID.randomUUID();
        cartDAOImpl.addToCart(uuid);

        Map<UUID, Integer> cart = cartDAOImpl.getCart();
        assertTrue(cart.isEmpty());


        //adding the first book from the collection
        BookDB bookDB = bookStoreDBHelper.getBooks().get(0);
        cartDAOImpl.addToCart(bookDB.getId());

        cart = cartDAOImpl.getCart();
        assertTrue(cart.size() == 1);
        assertTrue(cart.containsKey(bookDB.getId()));
        assertTrue(cart.get(bookDB.getId()) == 1); //making sure that only one book has been added


        //adding the first book for the second time
        cartDAOImpl.addToCart(bookDB.getId());

        cart = cartDAOImpl.getCart();
        assertTrue(cart.size() == 1);
        assertTrue(cart.containsKey(bookDB.getId()));
        assertTrue(cart.get(bookDB.getId()) == 2); //making sure that only one book has been added
    }

    @Test
    public void testRemoveFromCart() {

        //adding the first book  from the collection
        BookDB bookDB = bookStoreDBHelper.getBooks().get(0);
        cartDAOImpl.addToCart(bookDB.getId()); //quantity = 1
        cartDAOImpl.addToCart(bookDB.getId()); //quantity = 2

        //attempting to remove a book that doesn't exist
        UUID uuid = UUID.randomUUID();
        cartDAOImpl.removeFromCart(uuid);

        Map<UUID, Integer> cart = cartDAOImpl.getCart();
        assertTrue(cart.size() == 1);
        assertTrue(cart.containsKey(bookDB.getId()));
        assertTrue(cart.get(bookDB.getId()) == 2);

        //removing 1 book from the cart
        cartDAOImpl.removeFromCart(bookDB.getId());
        assertTrue(cart.size() == 1);
        assertTrue(cart.containsKey(bookDB.getId()));
        assertTrue(cart.get(bookDB.getId()) == 1);

        //removing the last book from the cart
        cartDAOImpl.removeFromCart(bookDB.getId());
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testBuy() {
        //adding the first book from the collection
        BookDB bookA = bookStoreDBHelper.getBooks().get(0);
        cartDAOImpl.addToCart(bookA.getId()); //quantity = 1
        cartDAOImpl.addToCart(bookA.getId()); //quantity = 2

        //adding the first book from the collection
        BookDB bookB = bookStoreDBHelper.getBooks().get(6); //gets the last book which is not in stock
        cartDAOImpl.addToCart(bookB.getId()); //quantity = 1

        int [] statuses = cartDAOImpl.buy(Arrays.asList(bookA.getId(), bookB.getId()));

        assertTrue(statuses.length == 2);
        assertTrue(statuses[0] == BookHelper.OK);
        assertTrue(statuses[1] == BookHelper.NOT_IN_STOCK);


        //make sure that the cart is now empty
        assertTrue(cartDAOImpl.getCart().isEmpty());
    }
}
