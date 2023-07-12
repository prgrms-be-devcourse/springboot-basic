package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Objects;
import java.util.UUID;
import com.example.voucher.constant.VoucherType;

public class PercentDiscountVoucher implements Voucher {

    private static final int PERCENT_DIVISOR = 100;

    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(long percent) {
        validatePercent(percent);

        this.voucherId = UUID.randomUUID();
        this.percent = percent;
    }

    protected PercentDiscountVoucher(UUID voucherId, long percent) {
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
    public long discount(long originalAmount) {
        validatePositive(originalAmount);

        double discountPercent = percent / PERCENT_DIVISOR;
        double discountAmount = originalAmount * discountPercent;
        long discountedAmount = originalAmount - (long)discountAmount;

        return discountedAmount;
    }

    private void validatePercent(long percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException(MESSAGE_ERROR_RANGE_CONSTRAINT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher)o;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }

}
