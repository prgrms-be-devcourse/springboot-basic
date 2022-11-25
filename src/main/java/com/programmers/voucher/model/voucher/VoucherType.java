package com.programmers.voucher.model.voucher;

import com.programmers.voucher.io.Message;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2", PercentDiscountVoucher::new);

    private final String voucherType;
    private final BiFunction<UUID, Long, Voucher> converter;

    VoucherType(String voucherType, BiFunction<UUID, Long, Voucher> converter) {
        this.voucherType = voucherType;
        this.converter = converter;
    }

    public String getVoucherType() {
        return voucherType;
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
}
