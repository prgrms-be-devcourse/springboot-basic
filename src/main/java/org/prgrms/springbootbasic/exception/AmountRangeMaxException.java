package org.prgrms.springbootbasic.exception;

public class AmountRangeMaxException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public AmountRangeMaxException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
