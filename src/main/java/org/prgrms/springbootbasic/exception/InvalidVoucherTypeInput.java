package org.prgrms.springbootbasic.exception;

public class InvalidVoucherTypeInput extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidVoucherTypeInput(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
