package exceptions;

/**
 * XML validation exception class
 *
 * @author Vera Shavela
 */
public class XMLException extends Exception {
    /**
     * Constructor with specified string
     *
     * @param message string
     */
    public XMLException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public XMLException(String message, Throwable e) {
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
