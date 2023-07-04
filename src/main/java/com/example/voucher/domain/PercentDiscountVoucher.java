package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.validator.VoucherValidator;

public class PercentDiscountVoucher implements Voucher {

    private static final int PERCENT_DIVISOR = 100;

    VoucherType voucherType = VoucherType.PERCNET_DISCOUNT;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        VoucherValidator.validatePercent(percent);
        this.voucherId = voucherId;
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
        VoucherValidator.validateNonZero(beforeAmount);
        VoucherValidator.validatePositive(beforeAmount);

        double discountPercent = percent / PERCENT_DIVISOR;
        double discountAmount = beforeAmount * discountPercent;
        long discountedAmount = beforeAmount - (long)discountAmount;

        return discountedAmount;
    }

}
