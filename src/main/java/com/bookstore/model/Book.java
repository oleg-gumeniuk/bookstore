package main.java.com.bookstore.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
public class Book {

    public Book() {}

    public Book(String author, String title, BigDecimal price) {
        this.author     = author;
        this.title      = title;
        this.price      = price;
    }

    /**
     * A unique book id
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
     * Book's quantity
     */
    private int quantity;




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
