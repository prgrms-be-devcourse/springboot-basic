package org.prgrms.springbootbasic.exception;

public class InvalidCustomerIdException extends ServiceException {

    private static final String INVALID_CUSTOMER_ID_EXP_MSG = "해당 아이디의 Customer가 존재하지 않습니다.";

    @Override
    public String getMessage() {
        return INVALID_CUSTOMER_ID_EXP_MSG;
    }
}
