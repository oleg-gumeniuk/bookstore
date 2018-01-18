package main.java.com.bookstore.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-16
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public interface CartDAO {

    /**
     * Adds a book into a user cart
     * @param bookID is an ID of a book being added
     */
    public void addToCart(UUID bookID);

    /**
     * Removes a book from a user cart
     * @param bookID is and id of a book being removed
     */
    public void removeFromCart(UUID bookID);

    /**
     * Gets all of the books that are currently in the user cart
     * @return a content of a user cart
     */
    public Map<UUID, Integer> getCart();

    /**
     * Buys the books from the cart
     * @param bookIDs is a list of book IDs to buy
     * @return an array that contains the status for every purchased book, e.g. OK(0), NOT_IN_STOCK(1) or DOES_NOT_EXIST(2)
     */
    public int[] buy(List<UUID> bookIDs);
}
