package org.prgrms.springbootbasic.exception;

public class InvalidCustomerIdException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidCustomerIdException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
