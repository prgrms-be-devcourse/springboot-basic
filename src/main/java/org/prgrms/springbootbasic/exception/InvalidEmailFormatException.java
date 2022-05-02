package org.prgrms.springbootbasic.exception;

public class InvalidEmailFormatException extends ServiceException {

    private final String errorMsg;

    public InvalidEmailFormatException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
