package org.prgrms.springbootbasic.exception;

public class PercentRangeMinException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public PercentRangeMinException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
