package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        if (amount <= 0) throw new VoucherException("Voucher discount amount should be positive.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return String.format("%s, %d", voucherId, amount);
    }
}
