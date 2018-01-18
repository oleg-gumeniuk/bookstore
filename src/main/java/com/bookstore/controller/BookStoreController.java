package main.java.com.bookstore.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: olegg
 * Date: 2018-01-15
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class BookStoreController {

    /**
     * Reads a book store data from a provided input file
     */
    public List<String> loadFromFile(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        List<String> booksDataStr = new ArrayList<String>();

        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            br = new BufferedReader(new InputStreamReader(in));

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                booksDataStr.add(sCurrentLine);
            }

        } catch (IOException e) {
            System.out.println(String.format("File '%s' not found. Exiting the store!", fileName));
            System.exit(0);

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                System.out.println(String.format("File '%s' not found. Exiting the store!", fileName));
                System.exit(0);
            }

        }

        return booksDataStr;
    }
}
