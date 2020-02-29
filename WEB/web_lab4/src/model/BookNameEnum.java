package model;

/**
 * class of books' names in library
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public enum BookNameEnum {

    WAR_AND_PEACE("War and Peace"),
    GREEN_MILE("Green mile"),
    IT("It"),
    FALL_EXILE_KINGDOM("The fall, exile and the kingdom"),
    HALLUCINATION("Hallucination");

    public String name;

    BookNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return "Book : " + name;
    }
}
