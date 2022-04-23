package org.prgrms.springbootbasic.exception;

public class InvalidVoucherTypeInput extends ServiceException {

    private final String ERROR_MSG;

    public InvalidVoucherTypeInput(String errorMsg) {
        this.ERROR_MSG = errorMsg;
    }

    @Override
    public String getMessage() {
        return ERROR_MSG;
    }
}
