package org.prgrms.kdt.domain.voucher.model;

import org.prgrms.kdt.domain.voucher.types.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long discountPrice;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT;

    public FixedAmountVoucher(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discountPrice = discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscountValue() {
        return discountPrice;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountPrice;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
