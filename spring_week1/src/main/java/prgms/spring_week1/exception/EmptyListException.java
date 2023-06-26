package prgms.spring_week1.exception;

public class EmptyListException extends RuntimeException {
    public EmptyListException() {
    }

    public EmptyListException(String message) {
        super(message);
    }
}
