package main.java.com.bookstore.helpers;

import main.java.com.bookstore.dao.impl.BookDAOImpl;
import main.java.com.bookstore.dao.impl.CartDAOImpl;
import main.java.com.bookstore.model.Book;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * A class that hold the menu business logic methods
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 2018-01-15
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public class MenuManager {

    BookDAOImpl bookDAOImpl;
    CartDAOImpl cartDAOImpl;
    Scanner scanner;

    public MenuManager(BookDAOImpl bookDAOImpl, CartDAOImpl cartDAOImpl, Scanner scanner) {
        this.bookDAOImpl = bookDAOImpl;
        this.cartDAOImpl = cartDAOImpl;
        this.scanner = scanner;
    }

    /**
     * Lists all the books available in the store
     * @return a list of books
     */
    public List<Book> listAll() {
        printSeparator();

        System.out.println("The following books are available:");
        List<Book> books = bookDAOImpl.findAllBooks();
        printBooks(books);

        printSeparator();

        return books;
    }

    /**
     * Searches a book/books by a provided searchString
     * @return a list of found books
     */
    public List<Book> searchBook() {
        printSeparator();
        System.out.println("Please enter a title or an author name:");

        String searchString = scanner.next();

        printSeparator();

        System.out.println("The following books were found:");
        List<Book> books = bookDAOImpl.findBooks(searchString);
        printBooks(books);


        printSeparator();

        return books;
    }


    /**
     * Adds a specified book to the cart
     */
    public void addToCart() {
        printSeparator();

        List<Book> foundBooks = bookDAOImpl.findAllBooks();
        printBooks(foundBooks);

        printSeparator();

        boolean isBookAdded = false;

        do {
            System.out.println("Please enter a book number:");
            String bookIndexStr = scanner.next();

            int bookIndex = -1;
            try {
                bookIndex = Integer.parseInt(bookIndexStr);
            } catch (NumberFormatException ex) {}

            if (bookIndex > 0 && bookIndex <= foundBooks.size()) {
                Book book = foundBooks.get(bookIndex - 1);

                if (book != null) {

                    cartDAOImpl.addToCart(book.getId());

                    System.out.println("The following book was added to your cart:");
                    printBook(book);

                    isBookAdded = true;
                }

            } else {
                System.out.print("The book doesn't exist. ");
            }


        } while (!isBookAdded);

        printSeparator();
    }

    /**
     * Removes a specified book from the cart
     */
    public void removeFromCart() {
        showCart();

        Map<UUID, Integer> cart = cartDAOImpl.getCart();
        List<UUID> bookIDs = new ArrayList<UUID>(cart.keySet());

        Map<Integer, UUID> index2bookID = new HashMap<Integer, UUID>();
        for (int index = 0; index < bookIDs.size(); index++) {
            UUID bookID = bookIDs.get(index);

            index2bookID.put(index, bookID);
        }

        boolean isBookRemoved = false;
        do {
            System.out.println("Please enter a book number:");
            String bookIndexStr = scanner.next();

            int bookIndex = -1;
            try {
                bookIndex = Integer.parseInt(bookIndexStr);
            } catch (NumberFormatException ex) {
                System.out.print("Invalid number. ");
            }

            if (bookIndex > 0 && bookIndex <= index2bookID.size()) {
                UUID bookID = index2bookID.get(bookIndex - 1);

                if (bookID != null) {

                    cartDAOImpl.removeFromCart(bookID);

                    System.out.println("The book has been successfully removed");

                    isBookRemoved = true;
                }

            } else {
                System.out.print("The book doesn't exist. ");
            }


        } while (!isBookRemoved);

        printSeparator();
    }

    /**
     * Shows the content of the cart
     */
    public void showCart() {
        printSeparator();

        Map<UUID, Integer> cartData = cartDAOImpl.getCart();

        if (cartData.isEmpty()) {
            System.out.println("Your Cart is empty.");
            printSeparator();
            return;
        }

        System.out.println("Your Cart:");

        Map<UUID, Book> id2book = new LinkedHashMap<UUID, Book>();
        List<Book> books = bookDAOImpl.findAllBooks();
        for (Book book : books) {
            id2book.put(book.getId(), book);
        }

        BigDecimal total = new BigDecimal(0);
        int index = 1;
        for (Map.Entry<UUID, Integer> entry : cartData.entrySet()) {
            UUID bookID = entry.getKey();
            Integer count = entry.getValue();
            Book book = id2book.get(bookID);


            BigDecimal itemPrice = book.getPrice().multiply(new BigDecimal(count));
            total = total.add(itemPrice);

            printBook(book, index, book.getQuantity());

            index++;
        }
        System.out.println("----------------------------------");
        System.out.println(String.format("TOTAL: %f", total));

        printSeparator();
    }


    /**
     * Buys all the books in the cart and clears the cart
     * @return a list of statuses for every purchased book
     */
    public int[] buy() {
        printSeparator();

        Map<UUID, Integer> cartData = cartDAOImpl.getCart();

        if (cartData.isEmpty()) {
            System.out.println("Your Cart is empty.");
            printSeparator();
            return null;
        }

        List<Book> books = bookDAOImpl.findAllBooks();
        Map<UUID, Book> id2book = new LinkedHashMap<UUID, Book>();
        for (Book book : books) {
            id2book.put(book.getId(), book);
        }

        int []result = cartDAOImpl.buy(new ArrayList<UUID>(cartData.keySet()));

        int index = 0;
        for (Map.Entry<UUID, Integer> entry : cartData.entrySet()) {
            UUID bookID = entry.getKey();
            Book book = id2book.get(bookID);

            printBookReceipt(book, result[index++]);
        }

        printSeparator();

        return result;
    }

    /**
     * Adds a new book into the store
     */
    public void addNewBook() {
        printSeparator();

        System.out.println("Please enter book's author:");
        String author = scanner.next();

        System.out.println("Please enter book's title:");
        String title = scanner.next();

        BigDecimal price = null;
        boolean isValidNumber = false;
        do {
            System.out.println("Please enter book's price:");
            String priceString = scanner.next();


            try {
                price = BookHelper.parsePrice(priceString);
                if (price.compareTo(BigDecimal.ZERO) > 0) {
                    isValidNumber = true;
                } else {
                    System.out.print("Invalid number. ");
                }
            } catch (ParseException ex) {
                System.out.print("Invalid number. ");
            }

        } while(!isValidNumber);

        Book book = new Book(author, title, price);
        bookDAOImpl.insert(book);
    }


    /**
     * Finishes the app execution
     */
    public void quit() {
        System.out.println("Thank you for visiting! Come back next time!");
    }

    private static void printSeparator() {
        System.out.println("");
        System.out.println("----------------------------");
        System.out.println("");
    }

    private static void printBooks(List<Book> books) {
        int index = 1;
        for (Book book : books) {
            printBook(book, index);
            index++;
        }
    }

    private static void printBook(Book book, int index) {
        printBook(book, index, book.getQuantity());
    }

    private static void printBook(Book book, int index, int quantity) {
        System.out.println(
                String.format("[%s] \"%s\" by %s - %f, %d pcs." ,
                        index, book.getAuthor(), book.getTitle(), book.getPrice(), quantity)
        );
    }

    private static void printBook(Book book) {
        System.out.println(String.format("\"%s\" by %s - %f" , book.getAuthor(), book.getTitle(), book.getPrice()));
    }

    /**
     * Prints a receipt for a specified book and returns the status code
     * OK(0), NOT_IN_STOCK(1) or DOES_NOT_EXIST_TXT(2)
     * @param book
     * @param status
     */
    private static void printBookReceipt(Book book, int status) {
        String statusText = "";
        switch (status) {
            case BookHelper.OK:
                statusText = BookHelper.OK_TXT;
                break;

            case BookHelper.NOT_IN_STOCK:
                statusText = BookHelper.NOT_IN_STOCK_TXT;
                break;

            case BookHelper.DOES_NOT_EXIST:
                statusText = BookHelper.DOES_NOT_EXIST_TXT;
                break;

            default:
                throw new IllegalArgumentException("Unsupported status code!");
        }
        System.out.println(
                String.format("\"%s\" by %s - %s(%d)" ,
                        book.getAuthor(), book.getTitle(), statusText, status)
        );
    }
}
