package org.prgrms.springbootbasic.exception;

public class InvalidVoucherIdException extends ServiceException {

    private final String errorMsg;

    public InvalidVoucherIdException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
