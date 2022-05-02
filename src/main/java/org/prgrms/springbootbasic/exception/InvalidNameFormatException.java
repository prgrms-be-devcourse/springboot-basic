package org.prgrms.springbootbasic.exception;

public class InvalidNameFormatException extends ServiceException {

    private final String errorMsg;

    public InvalidNameFormatException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
