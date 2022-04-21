package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends AbstractVoucher {

    public static final long MAX_AMOUNT = 10000;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.FIXED);
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount > MAX_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_AMOUNT));

        this.amount = amount;
    }


    @Override
    public long getDiscountValue() {
        return this.amount;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + getVoucherId() +
                ", voucherType=" + getVoucherType() +
                ", amount=" + amount +
                '}';
    }
}
