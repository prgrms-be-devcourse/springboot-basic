package org.prgrms.springbootbasic.exception;

public class AmountRangeMinException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public AmountRangeMinException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
