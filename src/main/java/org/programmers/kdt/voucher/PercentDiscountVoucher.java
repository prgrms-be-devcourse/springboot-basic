package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final int MAXIMUM_DISCOUNT_PERCENT = 100;
    private final int MINIMUM_DISCOUNT_PERCENT = 0;

    private final UUID voucherId;
    private final long percent;

    private VoucherStatus status;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        if (percent < MINIMUM_DISCOUNT_PERCENT || percent > MAXIMUM_DISCOUNT_PERCENT) {
            throw new RuntimeException(MessageFormat.format("Invalid Discount Percentage! Valid Range : [{0}, {1}]",
                    MINIMUM_DISCOUNT_PERCENT, MAXIMUM_DISCOUNT_PERCENT));
        }
        this.percent = percent;
        status = VoucherStatus.VALID;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long getDiscountAmount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
