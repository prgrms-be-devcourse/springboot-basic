package org.prgms.springbootbasic.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID uuid, VoucherType voucherType, long amount) {
        super(uuid, voucherType, amount);
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("invalid amount");
        }
    }

    @Override
    public long discount(long beforeAmount) {
        return beforeAmount - this.getAmount();
    }

    @Override
    public boolean isValidAmount(long amount) {
        return amount >= 0;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "uuid=" + uuid +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
