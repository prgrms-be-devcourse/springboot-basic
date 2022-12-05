package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final long MAX_AMOUNT = 100L;
    private static final long MIN_AMOUNT = 0L;

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this(voucherId, null, amount, false, createdAt, expiredAt);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID ownerId, long amount, boolean used, LocalDateTime createdAt, LocalDateTime expiredAt) {
        super(voucherId, ownerId, amount, "PERCENT", used, createdAt, expiredAt);
        if (amount <= MIN_AMOUNT) throw new VoucherException("Voucher discount percent should be positive.");
        if (amount > MAX_AMOUNT) throw new VoucherException("Voucher discount percent cannot be bigger than 100.");
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }


}
