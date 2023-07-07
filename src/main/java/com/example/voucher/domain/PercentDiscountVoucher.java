package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.Validator;

public class PercentDiscountVoucher implements Voucher {

    private static final int PERCENT_DIVISOR = 100;

    VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(long percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;

    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeAmount) {
        Validator.validateNonZero(beforeAmount);
        Validator.validatePositive(beforeAmount);

        double discountPercent = percent / PERCENT_DIVISOR;
        double discountAmount = beforeAmount * discountPercent;
        long discountedAmount = beforeAmount - (long)discountAmount;

        return discountedAmount;
    }

}
