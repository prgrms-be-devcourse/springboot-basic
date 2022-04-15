package org.prgrms.springbootbasic.exception;

public class PercentRangeMaxException extends ServiceException {

    private static final String PERCENT_MAX_RANGE_EXP_MSG = "percnet는 100보다 작거나 같아야합니다.";

    @Override
    public String getMessage() {
        return PERCENT_MAX_RANGE_EXP_MSG;
    }
}
