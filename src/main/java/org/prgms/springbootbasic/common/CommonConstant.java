package org.prgms.springbootbasic.common;

import java.time.LocalDateTime;

public class CommonConstant {
    public static final String CSV_PATTERN = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final int INPUT_FIXED_AMOUNT_VOUCHER = 1;
    public static final int INPUT_PERCENT_DISCOUNT_VOUCHER = 2;
    public static final LocalDateTime MIN_LOCAL_DATE_TIME = LocalDateTime.of(1000, 1, 1, 1, 1, 1);
    public static final LocalDateTime MAX_LOCAL_DATE_TIME = LocalDateTime.of(2060, 1, 1, 1, 1, 1);
}
