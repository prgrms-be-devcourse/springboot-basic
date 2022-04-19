package org.prgrms.springbootbasic.exception;

public class InvalidVoucherIdException extends ServiceException {

    private static final String INVALID_VOUCHER_ID_EXP_MSG = "해당 아이디의 Voucher가 존재하지 않습니다.";

    @Override
    public String getMessage() {
        return INVALID_VOUCHER_ID_EXP_MSG;
    }
}
