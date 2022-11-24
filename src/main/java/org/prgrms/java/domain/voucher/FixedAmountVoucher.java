package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long MIN_AMOUNT = 0L;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this(voucherId, null, amount, false, createdAt, expiredAt);
    }

    public FixedAmountVoucher(UUID voucherId, UUID ownerId, long amount, boolean used, LocalDateTime createdAt, LocalDateTime expiredAt) {
        super(voucherId, ownerId, amount, "FIXED", used, createdAt, expiredAt);
        if (amount <= MIN_AMOUNT) throw new VoucherException("Voucher discount amount should be positive.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

}
