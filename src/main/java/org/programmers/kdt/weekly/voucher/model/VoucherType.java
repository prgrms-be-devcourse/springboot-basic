package org.programmers.kdt.weekly.voucher.model;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {

    FIXED_AMOUNT_VOUCHER(1, "FixedAmountVoucher",
        FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER(2, "PercentDiscountVoucher",
        PercentDiscountVoucher::new);

    private final int number;
    private final String type;
    private final BiFunction<UUID, Integer, Voucher> voucherIdAndValue;

    VoucherType(int number, String type,
        BiFunction<UUID, Integer, Voucher> voucherIdAndValue) {
        this.number = number;
        this.type = type;
        this.voucherIdAndValue = voucherIdAndValue;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.number == number)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid number."));
    }

    public static VoucherType findByType(String type) {
        return Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.type.equals(type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid type."));
    }

    public Voucher create(UUID voucherId, Integer value) {
        return this.voucherIdAndValue.apply(voucherId, value);
    }
}