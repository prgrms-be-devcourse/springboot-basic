package org.prgrms.springbootbasic.exception;

public class InvalidEmailFormatException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidEmailFormatException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
