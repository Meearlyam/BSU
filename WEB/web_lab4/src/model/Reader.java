package model;

import exceptions.ReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * class that describes visitor of the library
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Reader implements Runnable {

    /**
     * logger to show process of transmitting readerBooks
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * id of last reader registered in library
     */
    private static int lastId = 1;


    /**
     * reader ID in library registry book
     */
    private int id;

    /**
     * getter of reader id
     */
    public int getId() {
        return id;
    }

    /**
     * reader name
     */
    private String name;

    /**
     * getter of reader name
     */
    public String getName() {
        return name;
    }

    /**
     * list of desired readerBooks
     */
    private ArrayList<Book> desiredBooks;

    /**
     * getter of desired book list
     */
    public List<Book> getDesiredBooks() {
        return desiredBooks;
    }

    /**
     * setter of desired readerBooks
     *
     * @param newDesiredBooks list of desired readerBooks to set
     */
    public void setDesiredBooks(ArrayList<Book> newDesiredBooks) {
        desiredBooks = newDesiredBooks;
    }

    /**
     * method that add book to desire list
     *
     * @param book book for adding to desire list
     */
    public void addToDesireList(Book book) {
        desiredBooks.add(book);
    }

    /**
     * list of readerBooks that reader is already keeping
     */
    private ArrayList<Book> readerBooks;

    /**
     * getter of book list
     */
    public ArrayList<Book> getReaderBooks() {
        return readerBooks;
    }

    /**
     * reader constructor
     *
     * @param name name of the reader
     */
    public Reader(String name, ArrayList<Book> desiredBooks) throws ReaderException {
        this.name = name;
        id = lastId++;
        if(id < 1)
            throw new ReaderException("Unreal reader ID");
        readerBooks = new ArrayList<>();
        this.desiredBooks = desiredBooks;
        Random random = new Random();
    }

    /**
     * method of taking book
     *
     * @param book book to take
     */
    public void takeBook(Book book) {
        readerBooks.add(book);
    }

    /**
     * method to give the book away
     *
     * @param book book to give away
     */
    public void giveBook(Book book) {
        readerBooks.remove(book);
    }

    /**
     * method of giving book away directly to another reader
     *
     * @param book book name to give away to another reader
     * @param reader name of the reader who wants this book
     */
    public void giveBookAway(Book book, Reader reader) {
        book.giveDirectly(this, reader);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        while(desiredBooks.size() > 0 || readerBooks.size() > 0) {
            for(int i = 0; i < desiredBooks.size(); i++) {
                Book desiredBook = desiredBooks.get(i);

                try {
                    logger.info(threadName + " try to get book : " + desiredBook.getName());
                    //desiredBook.bookLock.lock();
                    desiredBook.takeBook(this);
                    //desiredBook.takeBook(this);
                    logger.info(threadName + " took the book : " + desiredBook.getName());
                    readerBooks.add(desiredBook);
                    desiredBooks.remove(desiredBook);

                } catch (ReaderException e) {
                    logger.warn(threadName + " " + e.getMessage());
                }
            }

            for(int i = 0; i < readerBooks.size(); i++) {
                //if(readerBooks.size() > 0) {
                    try {
                        Book book = readerBooks.get(i);
                        logger.info(threadName + " try to read book : " + book);
                        Thread.sleep((long) book.getReadingTime());
                        logger.info(threadName + " read the book : " + book);
                        readerBooks.remove(book);
                        book.returnBook();
                        //desiredBooks.remove(desiredBook);
                    } catch (InterruptedException e) {
                        logger.warn("Somebody interrupted reading", e);
                    } catch (Exception e) {
                        logger.warn(threadName + " Nothing to read now...");
                    }
                //}
            }
        }
        logger.info(threadName + " successfully finished.");
    }
}
