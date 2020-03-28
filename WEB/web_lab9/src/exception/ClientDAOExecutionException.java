package exception;

public final class ClientDAOExecutionException extends Exception {

    /** Create new instance of ClientDAOExecutionException
     *
     * @param message - exception message
     */
    public ClientDAOExecutionException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     *
     * @param message string
     * @param e       error covered
     */
    public ClientDAOExecutionException(String message, Throwable e) {
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
