package com.programmers.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public enum VoucherType {
    FixedAmountVoucher("1", "fixedamountvoucher", com.programmers.domain.FixedAmountVoucher::new),
    PercentDiscountVoucher("2", "percentdiscountvoucher", com.programmers.domain.PercentDiscountVoucher::new);

    private static final Logger log = LoggerFactory.getLogger(VoucherType.class);

    private final String number;
    private final String name;
    private final VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor;

    VoucherType(String number, String name, VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor) {
        this.number = number;
        this.name = name;
        this.voucherConstructor = voucherConstructor;
    }

    public static VoucherType FindVoucherType(String input) {
        checkVoucherTypeInputEmpty(input);

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.number, input) || Objects.equals(voucherType.name, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("the invalid voucher type input found. input value = {}", input);
                    return new IllegalArgumentException();
                });
    }

    private static void checkVoucherTypeInputEmpty(String input) {
        if (input.isEmpty()) {
            log.error("The voucher type input not found.");
            throw new IllegalArgumentException();
        }
    }

    public static Voucher constructVoucher(String voucherTypeInput, String voucherName, Long discountValue) {
        VoucherType voucherType = FindVoucherType(voucherTypeInput);
        return voucherType.makeVoucher(voucherName, discountValue);
    }

    public static Voucher constructVoucher(String voucherTypeInput, UUID uuid, String voucherName, Long discountValue) {
        VoucherType voucherType = FindVoucherType(voucherTypeInput);
        return voucherType.makeVoucher(uuid, voucherName, discountValue);
    }

    private Voucher makeVoucher(String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(UUID.randomUUID(), voucherName, discountValue);
    }

    private Voucher makeVoucher(UUID uuid, String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(uuid, voucherName, discountValue);
    }
}
