package org.prgrms.springbootbasic.exception;

public class AlreadyAssignedVoucherException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public AlreadyAssignedVoucherException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
