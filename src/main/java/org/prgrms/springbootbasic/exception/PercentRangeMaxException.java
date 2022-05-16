package org.prgrms.springbootbasic.exception;

public class PercentRangeMaxException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public PercentRangeMaxException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
