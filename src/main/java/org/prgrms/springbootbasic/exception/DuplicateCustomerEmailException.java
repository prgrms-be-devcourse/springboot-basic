package org.prgrms.springbootbasic.exception;

public class DuplicateCustomerEmailException extends ServiceException {

    private final String errorMsg;

    public DuplicateCustomerEmailException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
