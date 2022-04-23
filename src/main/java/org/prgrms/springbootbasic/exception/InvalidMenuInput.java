package org.prgrms.springbootbasic.exception;

public class InvalidMenuInput extends ServiceException {

    private final String ERROR_MSG;

    public InvalidMenuInput(String errorMsg) {
        ERROR_MSG = errorMsg;
    }

    @Override
    public String getMessage() {
        return ERROR_MSG;
    }
}
