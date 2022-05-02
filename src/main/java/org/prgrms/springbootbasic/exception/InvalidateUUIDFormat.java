package org.prgrms.springbootbasic.exception;

public class InvalidateUUIDFormat extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidateUUIDFormat(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
