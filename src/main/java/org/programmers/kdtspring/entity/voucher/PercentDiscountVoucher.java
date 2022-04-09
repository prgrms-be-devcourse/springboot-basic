package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType = VoucherType.PercentDiscountVoucher;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
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
    public long getDiscount() {
        return this.percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
