package exception;

public final class OrderDAOExecutionException extends Exception {

    /** Create new instance of OrderDAOExecutionException
     *
     * @param message - exception message
     */
    public OrderDAOExecutionException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public OrderDAOExecutionException(String message, Throwable e) {
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
