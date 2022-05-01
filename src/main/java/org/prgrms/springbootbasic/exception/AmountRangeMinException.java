package org.prgrms.springbootbasic.exception;

public class AmountRangeMinException extends ServiceException {

    private final String errorMsg;

    public AmountRangeMinException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
