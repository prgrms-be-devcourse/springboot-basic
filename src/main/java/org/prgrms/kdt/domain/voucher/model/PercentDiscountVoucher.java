package org.prgrms.kdt.domain.voucher.model;

import org.prgrms.kdt.domain.voucher.types.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final int discountRate;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        if(percent > 100) throw new IllegalArgumentException("할인율은 100퍼센트를 초과할 수 없습니다.");
        this.voucherId = voucherId;
        this.discountRate = percent;
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
        return discountRate;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (discountRate / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", discountRate=" + discountRate +
                '}';
    }
}
