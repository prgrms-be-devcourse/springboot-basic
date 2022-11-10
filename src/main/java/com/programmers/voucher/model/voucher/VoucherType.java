package com.programmers.voucher.model.voucher;

import com.programmers.voucher.io.Message;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("FixedAmountVoucher"),
    PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
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

    @Override
    public String toString() {
        return voucherType;
    }
}
