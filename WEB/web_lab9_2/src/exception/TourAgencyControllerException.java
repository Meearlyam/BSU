package exception;

/**
 * dao object exception
 * @author Vera Shavela
 * @version 1.0.0
 */
public class TourAgencyControllerException extends Exception {
    /**
     * Constructor with specified string
     *
     * @param message string
     */
    public TourAgencyControllerException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public TourAgencyControllerException(String message, Throwable e) {
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
