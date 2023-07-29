package prgms.spring_week1.exception;

public class NoCustomerFoundException extends RuntimeException{
    public NoCustomerFoundException(String message) {
        super(message);
    }
}
