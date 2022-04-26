package org.prgrms.vouchermanager.domain.voucher.domain;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public class PercentDiscountVoucher extends AbstractVoucher {

    /* 할인 비율 */
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.PERCENT);

        checkArgument(amount>0 && amount<=100, "Percent should be between 0 and 100");

        this.percent = amount;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return Math.round(beforeDiscount * (100 - percent) / 100.0);
    }
}
