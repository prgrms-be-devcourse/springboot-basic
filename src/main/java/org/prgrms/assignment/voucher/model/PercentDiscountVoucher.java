package org.prgrms.assignment.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final String voucherName;
    private static int voucherNum = 0;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if(percent < 0 || percent >= 100)
            throw new IllegalArgumentException("percent should be higher than 0 or lower than 100");
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherName = getClass().getSimpleName() + voucherNum++;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getBenefit() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

}
