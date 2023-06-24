package com.programmers.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    FixedAmountVoucher("1", "fixedamountvoucher"),
    PercentDiscountVoucher("2", "percentdiscountvoucher");

    private static final Logger log = LoggerFactory.getLogger(VoucherType.class);

    private final String number;
    private final String name;

    VoucherType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static VoucherType findVoucherTypeByNumber(String input) {
        log.warn("The voucher type number input will be validated.");

        if (input.isEmpty()) {
            log.error("The voucher type number input not found.");
            throw new IllegalArgumentException();
        }

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.number, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("the invalid voucher type number input found. input value = {}", input);
                    return new IllegalArgumentException();
                });
    }

    public static VoucherType findVoucherTypeByName(String input) {
        log.warn("The voucher type name input will be validated.");

        if (input.isEmpty()) {
            log.error("The voucher type name input not found.");
            throw new IllegalArgumentException();
        }

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.name, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("The invalid voucher type name input found. input value = {}", input);
                    return new IllegalArgumentException();
                });
    }
}
