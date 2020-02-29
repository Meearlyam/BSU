package model.exceptions;

/**
 * Class representing server exception
 * @author Vera Shavela
 */
public class ServerException extends Exception {

    /**
     * Constructor with specified string
     * @param message string
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * Constructor with specified string and exception
     * @param message string
     * @param e covered error
     */
    public ServerException(String message, Throwable e){
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
