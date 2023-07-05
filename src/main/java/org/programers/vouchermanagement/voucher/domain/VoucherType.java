package org.programers.vouchermanagement.voucher.domain;

import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT((id , value, type) -> new Voucher(id, new FixedAmountPolicy(value), type)),
    PERCENT((id , value, type) -> new Voucher(id, new PercentDiscountPolicy(value), type));

    private final TriFunction<UUID, Integer, VoucherType, Voucher> function;

    VoucherType(TriFunction<UUID, Integer, VoucherType, Voucher> function) {
        this.function = function;
    }

    public Voucher createVoucher(UUID id, int value) {
        return function.apply(id, value, this);
    }

    boolean isFixedAmount() {
        return this == FIXED_AMOUNT;
    }

    boolean isPercent() {
        return this == PERCENT;
    }
}
