package org.prgrms.springbootbasic.exception;

public class PercentRangeMaxException extends ServiceException {

    private final String errorMsg;

    public PercentRangeMaxException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
