package model;

import exceptions.ReaderException;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * class that describes the work of book transmitting process
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Book {

    /**
     * time in milliseconds to transmit the book
     */
    private final int TRANSMIT_BOOK_TIME = 3000;

    /**
     * lock for the book
     */
    public Lock bookLock;

    /**
     * name of the book
     */
    private BookNameEnum name;

    /**
     * getter of book name
     */
    public BookNameEnum getName() {
        return name;
    }

    /**
     * time that reader reads the book
     */
    private double readingTime;

    /**
     * getter of reading time
     */
    public double getReadingTime() {
        return readingTime;
    }

    /**
     * setter of reading time
     */
    public void setReadingTime(int newReadingTime) {
        readingTime = newReadingTime;
    }

    /**
     * ID of reader who keeps the book at the moment
     */
    private int lastReaderId;

    /**
     * getter of last reader id
     */
    public int getLastReaderId() {
        return lastReaderId;
    }

    /**
     * setter of last reader id
     *
     * @param id of the reader
     */
    public void setLastReaderId(int id) {
        lastReaderId = id;
    }

    /**
     * flag that points is the book issued on hands or available only for reading in library
     */
    private boolean isIssuedOnHands;

    /**
     * synchronize bookSemaphore that shows that only one reader can keep the book
     */
    private Semaphore bookSemaphore;

    /**
     * getter of synchronized bookSemaphore
     */
    public Semaphore getBookSemaphore() {
        return bookSemaphore;
    }

    /**
     * constructor of book
     *
     * @param bookName
     */
    public Book(BookNameEnum bookName) {
        bookSemaphore = new Semaphore(1, false);
        this.name = bookName;
        Random random = new Random();
        int issuedOnHands = random.nextInt(30) % 2 + 1;
        if(issuedOnHands == 0) {
            isIssuedOnHands = true;
        }
        else {
            isIssuedOnHands = false;
        }
        readingTime = 15000;
    }

    /**
     * specified constructor of book
     *
     * @param bookName name of the book
     * @param isIssuedOnHands is book can be took home
     * @param readingTime time that is needed to read this book
     */
    public Book(BookNameEnum bookName, boolean isIssuedOnHands, int readingTime) {
        bookSemaphore = new Semaphore(1, false);
        this.name = bookName;
        this.isIssuedOnHands = isIssuedOnHands;
        this.readingTime = readingTime;
    }

    /**
     * method to determine current keeper(reader) of the book
     *
     * @param reader reader
     * @throws ReaderException if can't acquire semaphore
     * @return reader current reader
     */
    public int takeBook(Reader reader) throws ReaderException{
        if(tryTakeBook()) {
            this.lastReaderId = reader.getId();
            return this.lastReaderId;
        }
        else {
            throw new ReaderException("Cannot take this book yet");
        }
    }

    /**
     * try to take desired book
     */
    public boolean tryTakeBook() {
        return bookSemaphore.tryAcquire();
    }

    /**
     * method to give book directly from one reader to another
     */
    public void giveDirectly(Reader oldReader, Reader newReader) {
        oldReader.giveBook(this);
        this.lastReaderId = newReader.getId();
        newReader.takeBook(this);
    }

    /**
     * returns book to library
     */
    public void returnBook() {
        this.lastReaderId = 0;
        bookSemaphore.release();
    }

    @Override
    public String toString() {
        return "Book : \'" + name +
                "\', Reader ID : " + lastReaderId;
    }
}
