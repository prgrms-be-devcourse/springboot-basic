package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("FIXED", 1, "금액 할인"),
    PERCENT_DISCOUNT("PERCENT", 2, "퍼센트 할인");

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommand.class);
    private final String code;
    private final int number;
    private final String text;

    VoucherType(String code, int number, String text) {
        this.code = code;
        this.number = number;
        this.text = text;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.isEqualTo(number))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("{} => {}", ErrorMessage.INVALID_COMMAND, number);
                    return new InvalidCommandException(ErrorMessage.INVALID_COMMAND);
                });
    }

    private boolean isEqualTo(int number) {
        return this.number == number;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }

    public String getCode() {
        return code;
    }
}
