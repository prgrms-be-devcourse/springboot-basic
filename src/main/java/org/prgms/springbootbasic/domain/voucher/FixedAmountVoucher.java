package org.prgms.springbootbasic.domain.voucher;

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
                "uuid=" + voucherId +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
