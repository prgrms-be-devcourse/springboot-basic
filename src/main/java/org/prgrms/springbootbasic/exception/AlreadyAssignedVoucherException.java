package org.prgrms.springbootbasic.exception;

public class AlreadyAssignedVoucherException extends ServiceException {

    private static final String ALREADY_ASSIGNED_VOUCHER_EXP_MSG = "이미 할당된 바우처입니다.";

    @Override
    public String getMessage() {
        return ALREADY_ASSIGNED_VOUCHER_EXP_MSG;
    }
}
