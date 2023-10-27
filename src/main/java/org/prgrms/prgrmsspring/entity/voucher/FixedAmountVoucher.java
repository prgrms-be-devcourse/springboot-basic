package org.prgrms.prgrmsspring.entity.voucher;

import org.prgrms.prgrmsspring.domain.VoucherType;

import java.util.UUID;


public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount, VoucherType.FIXED_AMOUNT.getTitle());
        if (amount < 0) throw new IllegalArgumentException("고정 할인 금액은 음수가 될 수 없습니다.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
