package org.prgrms.springbootbasic.exception;

public class InvalidCustomerIdException extends ServiceException {

    private final String errorMsg;

    public InvalidCustomerIdException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
