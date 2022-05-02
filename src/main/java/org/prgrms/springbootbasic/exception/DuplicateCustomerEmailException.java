package org.prgrms.springbootbasic.exception;

public class DuplicateCustomerEmailException extends ServiceException {

    private final ServiceExceptionMessage errorMsg;

    public DuplicateCustomerEmailException(ServiceExceptionMessage errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg.getMessage();
    }
}
