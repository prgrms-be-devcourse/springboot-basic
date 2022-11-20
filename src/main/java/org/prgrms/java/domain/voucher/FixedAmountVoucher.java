package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime expiredAt) {
        this(voucherId, amount, false, expiredAt);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, boolean used, LocalDateTime expiredAt) {
        super(voucherId, amount, "FIXED", used, expiredAt);
        if (amount <= MIN_AMOUNT) throw new VoucherException("Voucher discount amount should be positive.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return String.format("%s, %d, %s, %s, %s", voucherId, amount, type, expiredAt, used);
    }
}
