package org.prgrms.springbootbasic.exception;

public class AmountRangeMaxException extends ServiceException {

    private final String errorMsg;

    public AmountRangeMaxException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
