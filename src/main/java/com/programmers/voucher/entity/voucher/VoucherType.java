package com.programmers.voucher.entity.voucher;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED(1, "금액 할인"),
    PERCENT(2, "퍼센트 할인");

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommand.class);
    private final int number;
    private final String text;

    VoucherType(int number, String text) {
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
}
