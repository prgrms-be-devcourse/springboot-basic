package org.prgms.kdt.application.voucher.domain;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private static final long MIN_VOUCHER_AMOUNT = 0;

    private UUID voucherId;
    private UUID customerId;
    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT;
    private long discountAmount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long discountAmount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }
    @Override
    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    @Override
    public UUID getCustomerId() {
        return this.customerId;
    }

    @Override
    public long getDiscountValue() {
        return this.discountAmount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public void changeDiscountValue(long discountValue) {
        this.discountAmount = discountValue;
    }

    @Override
    public void validateDiscountValue(long discountAmount) {
        if (discountAmount <= MIN_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should more than %d", MIN_VOUCHER_AMOUNT));
        if (discountAmount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
    }
}
