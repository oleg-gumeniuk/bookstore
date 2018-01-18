package main.java.com.bookstore.controller;

import main.java.com.bookstore.dao.impl.BookDAOImpl;
import main.java.com.bookstore.dao.impl.CartDAOImpl;
import main.java.com.bookstore.db.BookStoreDBManager;
import main.java.com.bookstore.helpers.BookHelper;
import main.java.com.bookstore.helpers.MenuManager;
import main.java.com.bookstore.model.Book;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static main.java.com.bookstore.helpers.UserInputHelper.*;

public class Main {

    private static final String BOOK_STORE_DATA = "resources/bookstoredata.txt";

    public static BookStoreDBManager bookStoreDBHelper;
    public static BookDAOImpl bookDAOImpl;
    public static CartDAOImpl cartDAOImpl;

    public static void main(String[] args) {
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

        //Display a welcome message
        showWelcomeMessage();

        //Start reading user input
        readUserInput();
    }


    /**
     * Handles the user input to then call an appropriate method in the MenuManager
     */
    private static void readUserInput() {
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        MenuManager menuManager = new MenuManager(bookDAOImpl, cartDAOImpl, scanner);

        int selection = -1;

        do {

            showMenu();

            if (!scanner.hasNextInt()) {
                scanner.next();
            } else {
                selection = scanner.nextInt();
            }

            switch (selection) {
                case LIST_ALL_BOOKS:
                    menuManager.listAll();
                    break;

                case SEARCH_BOOK:
                    menuManager.searchBook();
                    break;

                case ADD_TO_CART:
                    menuManager.addToCart();
                    break;

                case REMOVE_FROM_CART:
                    menuManager.removeFromCart();
                    break;

                case SHOW_CART:
                    menuManager.showCart();
                    break;

                case BUY_BOOKS:
                    menuManager.buy();
                    break;

                case ADD_BOOK:
                    menuManager.addNewBook();
                    break;

                case QUIT:
                    menuManager.quit();
                    break;

                default:
                    System.out.println("Invalid input.");
            }

        } while (selection != QUIT);
    }

    /**
     * Displays the store's welcome message in the console
     */
    private static void showWelcomeMessage() {
        System.out.println("");
        System.out.println("-----======= Welcome to Contribe Books! =======-----");
        System.out.println("");
    }


    /**
     * Shows the store's menu in the console
     */
    private static void showMenu() {
        System.out.println("[1] - SHOW ALL BOOKS");
        System.out.println("[2] - SEARCH BY AUTHOR OR TITLE");
        System.out.println("");
        System.out.println("[3] - ADD TO CART");
        System.out.println("[4] - REMOVE FROM CART");
        System.out.println("[5] - SHOW CART");
        System.out.println("");
        System.out.println("[6] - BUY");
        System.out.println("");
        System.out.println("[7] - ADD NEW BOOK");
        System.out.println("");
        System.out.println("[8] - QUIT");
        System.out.println("");
        System.out.println("Please enter [1-8]: ");
    }
}
