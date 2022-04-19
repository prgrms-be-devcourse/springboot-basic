package org.prgrms.springbootbasic.exception;

public class AmountRangeMinException extends ServiceException {

    private static final String AMOUNT_MIN_RANGE_EXP_MSG = "amount는 0보다 작거나 같을 수 없습니다.";

    @Override
    public String getMessage() {
        return AMOUNT_MIN_RANGE_EXP_MSG;
    }
}
