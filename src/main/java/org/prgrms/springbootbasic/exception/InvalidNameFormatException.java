package org.prgrms.springbootbasic.exception;

public class InvalidNameFormatException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidNameFormatException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
