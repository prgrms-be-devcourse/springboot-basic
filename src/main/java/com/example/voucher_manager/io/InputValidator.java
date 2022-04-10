package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.voucher.VoucherType;

public class InputValidator {
    // about percent voucher
    private static final Long MIN_PERCENT = 1L;
    private static final Long MAX_PERCENT = 100L;

    // about fixed voucher
    private static final Long MIN_DISCOUNT_AMOUNT = 1L;

    // about discount information
    private static final String INTEGER_PATTERN = "-?\\d+";

    public VoucherType validateVoucherType(String type) {
        if (VoucherType.isValidated(type)) {
            return VoucherType.of(type);
        }
        return VoucherType.ERROR;
    }

    public CommandType validateCommandType(String command) {
        if (CommandType.isValidated(command)){
            return CommandType.of(command);
        }
        return CommandType.ERROR;
    }

    public boolean isInteger(String input) {
        if (input.matches(INTEGER_PATTERN)) {
            return true;
        }
        return false;
    }

    public boolean isCorrectRangeOfPercent(Long percent) {
        if (percent >= MIN_PERCENT && percent <= MAX_PERCENT) {
            return true;
        }
        return false;
    }

    public boolean isPositiveNumber(Long amount) {
        if (amount >= MIN_DISCOUNT_AMOUNT) {
            return true;
        }
        return false;
    }
}
