package org.prgrms.springbootbasic.exception;

public class InvalidMenuInput extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public InvalidMenuInput(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
