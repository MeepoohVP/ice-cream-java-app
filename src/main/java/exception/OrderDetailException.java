package exception;

public class OrderDetailException extends RuntimeException{
    public OrderDetailException() {
        super();
    }
    public OrderDetailException(String message) {
        super(message);
    }
    public OrderDetailException(Throwable cause) {
        super(cause);
    }
    public OrderDetailException(String message, Throwable cause) {
        super(message, cause);
    }
}
