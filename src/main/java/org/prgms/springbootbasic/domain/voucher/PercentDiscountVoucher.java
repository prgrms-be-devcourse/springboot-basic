package org.prgms.springbootbasic.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID uuid, VoucherType voucherType, long amount) {
        super(uuid, voucherType, amount);
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("invalid amount");
        }
    }

    @Override
    public long discount(long beforeAmount) {
        return beforeAmount - ((beforeAmount * this.getAmount()) / 100);
    }

    @Override
    public boolean isValidAmount(long amount) {
        return amount >= 0 && amount <= 100;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "uuid=" + voucherId +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
