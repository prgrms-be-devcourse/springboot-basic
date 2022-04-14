package org.prgrms.springbootbasic.exception;

public class DuplicateCustomerEmailException extends RuntimeException {

    private static final String DUPLICATE_EMAIL_EXP_MSG = "이메일이 중복되었습니다.";

    public DuplicateCustomerEmailException() {
        super();
    }

    public DuplicateCustomerEmailException(String message) {
        super(message);
    }

    public DuplicateCustomerEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCustomerEmailException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCustomerEmailException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return DUPLICATE_EMAIL_EXP_MSG;
    }
}
