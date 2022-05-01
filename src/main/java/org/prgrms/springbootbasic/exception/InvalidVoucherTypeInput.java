package org.prgrms.springbootbasic.exception;

public class InvalidVoucherTypeInput extends ServiceException {

    private final String errorMsg;

    public InvalidVoucherTypeInput(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
