package devcourse.springbootbasic.exception;

public class CustomerException extends RuntimeException {

    private CustomerException(CustomerErrorMessage customerErrorMessage) {
        super(customerErrorMessage.getMessage());
    }

    public static CustomerException of(CustomerErrorMessage customerErrorMessage) {
        return new CustomerException(customerErrorMessage);
    }
}
