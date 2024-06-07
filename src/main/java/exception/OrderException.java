package exception;

public class OrderException extends RuntimeException{
    public OrderException() {
        super();
    }
    public OrderException(String message) {
        super(message);
    }
    public OrderException(Throwable cause) {
        super(cause);
    }
    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
