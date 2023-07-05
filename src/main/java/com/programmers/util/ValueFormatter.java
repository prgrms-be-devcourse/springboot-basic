package com.programmers.util;

import com.programmers.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueFormatter {

    private static final Logger log = LoggerFactory.getLogger(ValueFormatter.class);

    private ValueFormatter() {
    }

    public static String reformatVoucherType(String voucherType) {
        return voucherType.trim().replace(" ", "").toLowerCase();
    }

    public static Long changeDiscountValueToNumber(String discountValue) {
        try {
            Long.parseLong(discountValue);
        } catch (NumberFormatException e) {
            log.error("The discount value input is not in numeric format. input value = {}", discountValue);
            throw new InvalidInputException("[ERROR] 입력하신 값이 유효하지 않습니다.");
        }
        return Long.parseLong(discountValue);
    }
}
