package org.prgrms.springbootbasic.exception;

public class PercentRangeMinException extends ServiceException {

    private final String errorMsg;

    public PercentRangeMinException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
