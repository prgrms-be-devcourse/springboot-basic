package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT_DISCOUNT;

    public FixedAmountVoucher(long discountValue) {
        validatePositive(discountValue);
        this.voucherId = UUID.randomUUID();
        this.discountValue = discountValue;
    }

    public FixedAmountVoucher(UUID voucherId, long discountValue) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    @Override

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountValue() {
        return discountValue;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long originalAmount) {
        validatePositive(originalAmount);
        validateGreaterThan(originalAmount, discountValue);

        return originalAmount - discountValue;
    }

    private void validateGreaterThan(long value, long threshold) {
        if (value <= threshold) {
            throw new IllegalArgumentException(
                String.format("{} {}", FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
        }
    }

}
