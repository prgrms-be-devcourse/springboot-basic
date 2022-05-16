package org.prgrms.springbootbasic.exception;

public class InvalidVoucherIdException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidVoucherIdException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
