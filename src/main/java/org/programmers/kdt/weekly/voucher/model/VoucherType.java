package org.programmers.kdt.weekly.voucher.model;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {

    FIXED_AMOUNT_VOUCHER(1, FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER(2, PercentDiscountVoucher::new);

    private final int number;
    private final BiFunction<UUID, Integer, Voucher> voucherIdAndValue;

    VoucherType(int number,
        BiFunction<UUID, Integer, Voucher> voucherIdAndValue) {
        this.number = number;
        this.voucherIdAndValue = voucherIdAndValue;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid number."));
    }

    public Voucher create(UUID voucherId, Integer value) {
        return this.voucherIdAndValue.apply(voucherId, value);
    }
}