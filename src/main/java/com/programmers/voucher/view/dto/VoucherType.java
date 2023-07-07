package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("fixed", "create a fixed amount voucher."),
    PERCENT_DISCOUNT("percent", "create a percent discount voucher.");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private final String name;
    private final String text;

    VoucherType(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public static VoucherType findByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.equals(name))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("{} => {}", ErrorMessage.INVALID_COMMAND, name);
                    return new InvalidCommandException(ErrorMessage.INVALID_COMMAND);
                });
    }

    private boolean equals(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
