package org.prgrms.voucherapplication.exception;

public class InvalidCustomerInformationTypeException extends RuntimeException{
    public InvalidCustomerInformationTypeException() {
        super(ErrorType.INVALID_CUSTOMER_INFORMATION_TYPE.getMessage());
    }

    public InvalidCustomerInformationTypeException(String message) {
        super(message);
    }

    public InvalidCustomerInformationTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCustomerInformationTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidCustomerInformationTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
