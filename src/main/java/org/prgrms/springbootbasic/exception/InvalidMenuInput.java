package org.prgrms.springbootbasic.exception;

public class InvalidMenuInput extends ServiceException {

    private final String errorMsg;

    public InvalidMenuInput(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
