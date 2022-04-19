package org.prgrms.springbootbasic.exception;

public class DuplicateCustomerEmailException extends ServiceException {

    private static final String DUPLICATE_EMAIL_EXP_MSG = "이메일이 중복되었습니다.";

    public DuplicateCustomerEmailException() {
        super();
    }

    @Override
    public String getMessage() {
        return DUPLICATE_EMAIL_EXP_MSG;
    }
}
