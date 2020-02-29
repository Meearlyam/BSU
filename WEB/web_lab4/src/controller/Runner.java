package controller;

import model.*;
import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * class of runner that starts all books' processes
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Runner {

    /**
     * logger that saves work of processes in logfile
     */
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Library starts work");

        Library library = new Library();

        ArrayList<Book> desiredBooks = new ArrayList<>();
        for(int i = 0; i < 3; i++)
            desiredBooks.add(library.getBooks().get(i));


        Thread firstThread = null;
        try {
            firstThread = new Thread(new Reader("Eddie Godson", desiredBooks));
        } catch (ReaderException e) {
            logger.warn("Reader id isn't correct");
        }

        Thread secondThread = null;
        try {
            secondThread = new Thread(new Reader("Mary Kelly", desiredBooks));
        } catch (ReaderException e) {
            logger.warn("Reader id isn't correct");
        }

        Thread thirdThread = null;
        try {
            thirdThread = new Thread(new Reader("Ann But", desiredBooks));
        } catch (ReaderException e) {
            logger.warn("Reader id isn't correct");
        }


        try {
            firstThread.start();
            secondThread.start();
            thirdThread.start();
        } catch (NullPointerException e) {
            logger.error(e);
        }


        try {
            firstThread.join();
        } catch (InterruptedException e) {
            logger.warn(e);
        }
        try {
            secondThread.join();
        } catch (InterruptedException e) {
            logger.warn(e);
        }
        try {
            thirdThread.join();
        } catch (InterruptedException e) {
            logger.warn(e);
        }
    }
}
