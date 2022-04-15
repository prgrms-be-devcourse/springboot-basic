package org.prgrms.springbootbasic.exception;

public class PercentRangeMinException extends ServiceException {

    private static final String PERCENT_MIN_RANGE_EXP_MSG = "percent는 0보다 커야합니다.";

    @Override
    public String getMessage() {
        return PERCENT_MIN_RANGE_EXP_MSG;
    }
}
