package test.java.com.bookstore.helpers;


import main.java.com.bookstore.helpers.BookHelper;
import main.java.com.bookstore.model.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-17
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class BookHelperTests {

    @Test
    public void parseBook() {

        String title = "007 Adventure";
        String author = "James Bond";
        String price  = "700.07";
        String quantity = "1";

        String bookString = String.join(";", title, author, price, quantity);
        Book book = BookHelper.parseBook(bookString);
        assertTrue(author.equals(book.getAuthor()));

        //let's test it with less parameters
        bookString = String.join(";", title, author, price);
        book = BookHelper.parseBook(bookString);
        assertTrue(book == null);

        //let's test it with more parameters
        bookString = String.join(";", title, author, price, quantity, quantity);
        book = BookHelper.parseBook(bookString);
        assertTrue(book == null);

        //let's test it with an invalid price
        price  = "fff";
        bookString = String.join(";", title, author, price, quantity);
        book = BookHelper.parseBook(bookString);
        assertTrue(book == null);

        //let's test it with an invalid quantity
        price  = "700.07";
        quantity  = "fff";
        bookString = String.join(";", title, author, price, quantity);
        book = BookHelper.parseBook(bookString);
        assertTrue(book == null);
    }

    @Test
    public void parsePrice() {

        //trying to parse an invalid price value
        try {
            BookHelper.parsePrice("fff");

            assertTrue(false);// shouldn't reach this line since we expect an exception
        } catch (ParseException ex) {}


        try {
            BigDecimal price = new BigDecimal(1234.55555555).setScale(3, RoundingMode.HALF_UP);
            BigDecimal parsedPrice = BookHelper.parsePrice("1,234.55555555");

            assertTrue(price.compareTo(parsedPrice) == 0);

        } catch (ParseException ex) {}
    }
}
