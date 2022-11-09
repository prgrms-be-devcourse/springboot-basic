package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount <= 0) throw new VoucherException("Voucher amount should be positive.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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
