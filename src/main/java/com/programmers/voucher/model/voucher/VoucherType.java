package com.programmers.voucher.model.voucher;

import com.programmers.voucher.io.Message;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", "FixedAmountVoucher", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2", "PercentDiscountVoucher", PercentDiscountVoucher::new);

    private final String voucherType;
    private final String voucherName;
    private final BiFunction<UUID, Long, Voucher> converter;

    VoucherType(String voucherType, String voucherName, BiFunction<UUID, Long, Voucher> converter) {
        this.voucherType = voucherType;
        this.voucherName = voucherName;
        this.converter = converter;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public static VoucherType toVoucherType(String inputString) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getVoucherType().equals(inputString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Message.WRONG_ORDER_MESSAGE.toString()));
    }

    public Voucher convertToVoucher(UUID voucherId, long discount) {
        return converter.apply(voucherId, discount);
    }

    @Override
    public String toString() {
        return voucherName;
    }
}
