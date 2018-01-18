package main.java.com.bookstore.db.table;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * This class is just an emulation of a corresponding Book MYSQL Table.
 * It's used to store the data that is loaded from a txt file
 *
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class BookDB {

    public BookDB() {
        this.id = UUID.randomUUID();
    }

    /**
     * A unique identifier
     */
    private UUID id;

    /**
     * A title of the book
     */
    private String title;

    /**
     * Books' author, consists of a firstname and a last name
     */
    private String author;

    /**
     * Book's price
     */
    private BigDecimal price;

    /**
     * The number of books left in the store
     */
    private int quantity;


    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
