package main.java.com.bookstore.dao.impl;

import main.java.com.bookstore.dao.CartDAO;
import main.java.com.bookstore.db.BookStoreDBManager;
import main.java.com.bookstore.db.table.BookDB;
import main.java.com.bookstore.helpers.BookHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-16
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class CartDAOImpl implements CartDAO {

    private static final Logger LOGGER = Logger.getLogger( CartDAOImpl.class.getName() );

    private BookStoreDBManager dbHelper;

    public CartDAOImpl(BookStoreDBManager dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Adds a book into a user cart
     * @param bookID is an ID of a book being added
     */
    @Override
    public void addToCart(UUID bookID) {
        try {
            //make sure that the book exists
            BookDB bookDB = dbHelper.findBookById(bookID);

            if (bookDB != null) {
                dbHelper.addToCart(bookID);
            }

        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Couldn't add a book to Cart");
        }
    }

    /**
     * Removes a book from a user cart
     * @param bookID is and id of a book being removed
     */
    @Override
    public void removeFromCart(UUID bookID) {
        try {
            dbHelper.removeFromCart(bookID);
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Couldn't remove a book from Cart");
        }


    }

    /**
     * Gets all of the books that are currently in the user cart
     * @return a content of a user cart
     */
    @Override
    public Map<UUID, Integer> getCart() {
        return dbHelper.getCart();
    }

    /**
     * Buys the books from the cart
     * @param bookIDs is a list of book IDs to buy
     * @return an array that contains the status for every purchased book, e.g. OK(0), NOT_IN_STOCK(1) or DOES_NOT_EXIST(2)
     */
    @Override
    public int[] buy(List<UUID> bookIDs) {
        Map<UUID, Integer> cart = getCart();
        int[] result = new int[cart.size()];

        Map<UUID, BookDB> id2book = new HashMap<UUID, BookDB>();
        List<BookDB> books = dbHelper.getBooks();
        for (BookDB bookDB : books) {
            id2book.put(bookDB.getId(), bookDB);
        }


        int index = 0;
        for (Map.Entry<UUID, Integer> entry : cart.entrySet()) {
            UUID bookID = entry.getKey();
            Integer boughtCount = entry.getValue();
            BookDB bookDB = id2book.get(bookID);

            if (bookDB == null) { //if a book is not in the store
                result[index++] = BookHelper.DOES_NOT_EXIST;
            } else if (bookDB.getQuantity() < boughtCount) { //if store's availability is less than requested
                result[index++] = BookHelper.NOT_IN_STOCK;
            } else {
                result[index++] = BookHelper.OK;

                //decrease the number of books left
                int available = bookDB.getQuantity();
                bookDB.setQuantity(available - boughtCount);
            }
        }

        dbHelper.clearCart();

        return result;
    }
}
