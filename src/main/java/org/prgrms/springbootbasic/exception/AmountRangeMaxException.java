package org.prgrms.springbootbasic.exception;

public class AmountRangeMaxException extends ServiceException {

    private static final String AMOUNT_MAX_RANGE_EXP_MSG = "amount는 100000보다 작아야 합니다.";

    @Override
    public String getMessage() {
        return AMOUNT_MAX_RANGE_EXP_MSG;
    }
}
