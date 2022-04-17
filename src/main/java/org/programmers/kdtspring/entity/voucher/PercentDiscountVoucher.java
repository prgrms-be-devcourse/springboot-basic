package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final int percent;
    private final VoucherType voucherType = VoucherType.PercentDiscountVoucher;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        super(voucherId);
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, int percent) {
        super(voucherId, customerId);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public int getDiscount() {
        return this.percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
