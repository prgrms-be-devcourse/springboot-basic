package org.prgrms.springbootbasic.exception;

public class InvalidateUUIDFormat extends ServiceException {

    private static final String INVALID_UUID_FORMAT_EXP_MSG = "UUID 포멧이 아닙니다.";

    @Override
    public String getMessage() {
        return INVALID_UUID_FORMAT_EXP_MSG;
    }
}
