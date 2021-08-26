package org.programmers.kdt.voucher;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final int MAXIMUM_DISCOUNT_PERCENT = 100;
    private final int MINIMUM_DISCOUNT_PERCENT = 0;

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        if (percent < MINIMUM_DISCOUNT_PERCENT || percent > MAXIMUM_DISCOUNT_PERCENT) {
            throw new RuntimeException(MessageFormat.format("Invalid Discount Percentage! Valid Range : [{0}, {1}]",
                    MINIMUM_DISCOUNT_PERCENT, MAXIMUM_DISCOUNT_PERCENT));
        }
        this.percent = percent;
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
    public String toString() {
        return MessageFormat.format("<< Percent Discount Voucher >> \nID : {0}\nDiscount : {1}%", voucherId, percent);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || o.getClass() != PercentDiscountVoucher.class) {
            return false;
        }
        PercentDiscountVoucher voucher = (PercentDiscountVoucher)o;
        return voucherId.equals(voucher.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voucherId);
    }

}
