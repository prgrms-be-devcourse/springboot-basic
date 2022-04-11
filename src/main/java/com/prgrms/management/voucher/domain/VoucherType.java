package com.prgrms.management.voucher.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public enum VoucherType {
    FIXED((amount) -> new FixedAmountVoucher(amount)),
    PERCENT((amount) -> new PercentAmountVoucher(amount));

    private final Function<Long, Voucher> voucher;

    VoucherType(Function<Long, Voucher> voucher) {
        this.voucher = voucher;
    }

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + ":잘못된 입력 값입니다."));
    }


    public static long toLong(String amount) {
        try {
            return Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(VoucherType.class + ":올바른 숫자 형식이 아닙니다.");
        }
    }

    public Voucher createVoucher(Long amount) {
        return voucher.apply(amount);
    }
}
