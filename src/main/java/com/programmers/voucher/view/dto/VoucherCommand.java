package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;

import java.util.Arrays;

public enum VoucherCommand { //TODO VoucherType
    FIXED_AMOUNT("fixed", "create a fixed amount voucher."),
    PERCENT_DISCOUNT("percent", "create a percent discount voucher.");

    private final String code;
    private final String text;

    VoucherCommand(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static VoucherCommand findByCode(String code) {
        return Arrays.stream(VoucherCommand.values())
                .filter(menu -> menu.equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(ErrorMessage.INVALID_COMMAND));
    }

    private boolean equals(String code) {
        return this.code.equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
