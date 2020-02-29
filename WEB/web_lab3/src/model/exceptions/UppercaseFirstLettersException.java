package model.exceptions;

/**
 * Class representing invalid deleting specified word exception
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class UppercaseFirstLettersException extends Exception {
    /**
     * Constructor with specified string
     * @param message string
     */
    public UppercaseFirstLettersException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     * @param message string
     * @param e error covered
     */
    public UppercaseFirstLettersException(String message, Throwable e) {
        super(message, e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
