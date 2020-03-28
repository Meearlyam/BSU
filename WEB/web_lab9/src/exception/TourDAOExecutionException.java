package exception;

public final class TourDAOExecutionException extends Exception {

    /** Create new instance of TourDAOExecutionException
     *
     * @param message - exception message
     */
    public TourDAOExecutionException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public TourDAOExecutionException(String message, Throwable e) {
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
