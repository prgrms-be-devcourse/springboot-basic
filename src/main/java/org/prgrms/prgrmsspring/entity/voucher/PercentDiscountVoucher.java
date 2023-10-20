package org.prgrms.prgrmsspring.entity.voucher;

import org.prgrms.prgrmsspring.domain.VoucherType;

import java.util.UUID;


public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount, VoucherType.PERCENT_DISCOUNT.getTitle());
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + amount +
                '}';
    }
}
