package model;

import java.util.*;

/**
 * class that keeps all books of library
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Library {

    /**
     * list of books library poses
     */
    private ArrayList<Book> books;

    /**
     * getter of library books
     *
     * @return books of the library
     */
    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * registry book that shows who took the book
     */
    private HashMap<Book, Reader> registryBook;

    /**
     * reading room of the library
     */
    private ArrayList<Reader> readingRoom;


    /**
     * default constructor of library
     */
    public Library() {
        readingRoom = new ArrayList<>();
        books = new ArrayList<>();
        books.add(new Book(BookNameEnum.WAR_AND_PEACE, true, 1000));
        books.add(new Book(BookNameEnum.GREEN_MILE, false, 500));
        books.add(new Book(BookNameEnum.FALL_EXILE_KINGDOM, true, 400));
        books.add(new Book(BookNameEnum.HALLUCINATION, true, 700));
        books.add(new Book(BookNameEnum.IT, false, 900));
        registryBook = new HashMap<>();
    }

    /**
     * library constructor with additional books
     * @param newBooks another new books to insert to new library
     */
    public Library(ArrayList<Book> newBooks) {
        readingRoom = new ArrayList<>();
        books = new ArrayList<>();
        books.add(new Book(BookNameEnum.WAR_AND_PEACE, true, 10000));
        books.add(new Book(BookNameEnum.GREEN_MILE, false, 5000));
        books.add(new Book(BookNameEnum.FALL_EXILE_KINGDOM, true, 4000));
        books.add(new Book(BookNameEnum.HALLUCINATION, true, 7000));
        books.add(new Book(BookNameEnum.IT, false, 9000));

        books.addAll(newBooks);
        registryBook = new HashMap<>();
    }

    /**
     * taking randomly one of library books
     */
    public Book desireBook() {
        Random random = new Random();
        int i = random.nextInt(100) % 5;
        return books.get(i);
    }

    /**
     * method to add new book to library
     *
     * @param book name of new book
     */
    public void addNewBook(BookNameEnum book) {
        books.add(new Book(book));
    }

    /**
     * method to add new books to library
     *
     * @param newBooks list of new books
     */
    public void addNewBooks(ArrayList<Book> newBooks) {
        books.addAll(newBooks);
    }

    /**
     * register getting the book
     *
     * @param book book name
     * @param reader reader who takes this book
     */
    public void registerBookGetting(Book book, Reader reader) {
        registryBook.put(book, reader);
    }
}
