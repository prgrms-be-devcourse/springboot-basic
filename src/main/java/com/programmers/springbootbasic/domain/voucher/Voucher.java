package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.common.util.Validator;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;

    protected Voucher(UUID voucherId, VoucherType voucherType) {
        Validator.checkNullUuid(voucherId);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
    }

    public static Voucher createVoucher(UUID voucherId, VoucherType voucherType, int amountOrPercent) {
        return switch (voucherType) {
            case FIX -> new FixedAmountVoucher(voucherId, amountOrPercent);
            case PERCENT -> new PercentDiscountVoucher(voucherId, amountOrPercent);
        };
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public abstract Long getDiscountPrice(Long priceBeforeDiscount);

    public abstract int getAmountOrPercent();
}
