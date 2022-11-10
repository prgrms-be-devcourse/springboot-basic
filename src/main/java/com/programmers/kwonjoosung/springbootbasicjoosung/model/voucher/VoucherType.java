package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongVoucherTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private final String command;

    VoucherType(String command) {
        this.command = command;
    }

    public static VoucherType getVoucherType(String input) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.command.equals(input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Wrong VoucherType = {}", input);
                    return new WrongVoucherTypeException(input);
                });
    }
}
