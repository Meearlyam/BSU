package model.exceptions;

/**
 * Class representing client exceptions
 * @author Vera Shavela
 * @version 1.0.0
 */
public class ClientConnectionException extends Throwable {

    /**
     * Constructor with specified string
     * @param message string
     */
    public ClientConnectionException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exceptions
     * @param message string
     * @param e covered error
     */
    public ClientConnectionException(String message, Throwable e){
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
