package main.java.com.bookstore.helpers;

import main.java.com.bookstore.model.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An utility class that contains the methods that help to parse a book from a String value
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class BookHelper {

    private static final Logger LOGGER = Logger.getLogger( BookHelper.class.getName() );

    public static final int OK = 0;
    public static final int NOT_IN_STOCK = 1;
    public static final int DOES_NOT_EXIST = 2;

    public static final String OK_TXT = "OK";
    public static final String NOT_IN_STOCK_TXT = "NOT_IN_STOCK";
    public static final String DOES_NOT_EXIST_TXT = "DOES_NOT_EXIST";

    /**
     * Parses a String line that holds the information about the book and created a Book model object
     * @param bookStr is a line of text that holds the book data
     * @return a book model object
     */
    public static Book parseBook(String bookStr) {
        Book book = null;

        try {
            String[] bookData = bookStr.split(";");

            if (bookData.length != 4) {
                throw new IllegalArgumentException ("Invalid input data. A book should consist of 4 properties");
            }

            book = new Book();

            String title = bookData[0];
            book.setTitle(title);

            String author = bookData[1];
            book.setAuthor(author);

            BigDecimal price = parsePrice(bookData[2]);
            book.setPrice(price);

            int quantity = parseQuantity(bookData[3]);
            book.setQuantity(quantity);

        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
            return null;
        } catch (ParseException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
            return null;
        }

        return book;
    }

    public static BigDecimal parsePrice(String priceStr) throws ParseException {

        // Create a DecimalFormat that fits your requirements
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        // parse the string
        BigDecimal price = ((BigDecimal) decimalFormat.parse(priceStr)).setScale(3, RoundingMode.HALF_UP);

        return price;
    }

    private static int parseQuantity(String quantityStr) {
        return Integer.valueOf(quantityStr);
    }
}
