package org.voucherProject.voucherProject.voucher.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(exclude = {"voucherType"})
public class FixedAmountVoucher extends Voucher {

    private final long amount;
    @Nullable
    private final VoucherType voucherType = VoucherType.FIXED;

    private final int MIN_DISCOUNT_AMOUNT = 0;
    private final int MAX_DISCOUNT_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount, UUID customerId) {
        super(voucherId,customerId);
        validateAmount(amount);
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, long amount, @Nullable VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
        super(voucherId,voucherStatus,createdAt,customerId);
        validateAmount(amount);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount - amount < 0) {
            throw new IllegalArgumentException();
        }
        return beforeDiscount - amount;
    }

    @Override
    public long getHowMuch() {
        return this.amount;
    }

    private void validateAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT || amount == 0) {
            throw new IllegalArgumentException();
        }
    }
}
