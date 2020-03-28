package exception;

public final class TourTypeDAOExecutionException extends Exception {

    /** Create new instance of TourTypeDAOExecutionException
     *
     * @param message - exception message
     */
    public TourTypeDAOExecutionException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public TourTypeDAOExecutionException(String message, Throwable e) {
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
