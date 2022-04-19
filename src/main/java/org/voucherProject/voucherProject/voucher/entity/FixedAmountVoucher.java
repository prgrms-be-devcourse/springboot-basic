package org.voucherProject.voucherProject.voucher.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString(exclude = {"voucherId", "voucherType"})
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    @Nullable
    private final VoucherType voucherType = VoucherType.FIXED;
    @Nullable
    private VoucherStatus voucherStatus;
    private final LocalDateTime createdAt;
    private UUID customerId;

    private final int MIN_DISCOUNT_AMOUNT = 0;
    private final int MAX_DISCOUNT_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount, UUID customerId) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherStatus = VoucherStatus.VALID;
        this.createdAt = LocalDateTime.now();
        this.customerId = customerId;
    }

    public FixedAmountVoucher(UUID voucherId, long amount, @Nullable VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherStatus = voucherStatus;
        this.createdAt = createdAt;
        this.customerId = customerId;
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

    @Override
    public VoucherStatus getVoucherStatus() {
        return this.voucherStatus;
    }

    @Override
    public void useVoucher() {
        this.voucherStatus = VoucherStatus.EXPIRED;
    }

    @Override
    public void cancelVoucher() {
        this.voucherStatus = VoucherStatus.VALID;
    }

    private void validateAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT || amount == 0) {
            throw new IllegalArgumentException();
        }
    }
}
