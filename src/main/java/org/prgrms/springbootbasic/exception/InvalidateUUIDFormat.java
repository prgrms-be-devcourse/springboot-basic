package org.prgrms.springbootbasic.exception;

public class InvalidateUUIDFormat extends ServiceException {

    private final String errorMsg;

    public InvalidateUUIDFormat(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
