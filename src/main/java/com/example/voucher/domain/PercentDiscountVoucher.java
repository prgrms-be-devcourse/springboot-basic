package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class PercentDiscountVoucher implements Voucher {

    private static final int PERCENT_DIVISOR = 100;

    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    public PercentDiscountVoucher(long discountValue) {
        validatePercent(discountValue);

        this.voucherId = UUID.randomUUID();
        this.discountValue = discountValue;
    }

    public PercentDiscountVoucher(UUID voucherId, long discountValue) {
        validatePercent(discountValue);

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

        double discountPercent = discountValue / PERCENT_DIVISOR;
        double discountAmount = originalAmount * discountPercent;
        long discountedAmount = originalAmount - (long)discountAmount;

        return discountedAmount;
    }

    private void validatePercent(long percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException(MESSAGE_ERROR_RANGE_CONSTRAINT);
        }
    }

}
