package org.prgrms.springbootbasic.exception;

public class AlreadyAssignedVoucherException extends ServiceException {

    private final String errorMsg;

    public AlreadyAssignedVoucherException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
