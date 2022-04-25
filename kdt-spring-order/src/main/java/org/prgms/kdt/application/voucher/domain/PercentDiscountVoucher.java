package org.prgms.kdt.application.voucher.domain;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_RATE = 100;
    private static final long MIN_VOUCHER_RATE = 0;

    private UUID voucherId;
    private UUID customerId;
    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
    private long discountRate;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long discountRate, LocalDateTime createdAt) {
        validateDiscountValue(discountRate);
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.discountRate = discountRate;
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
        return this.discountRate;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * ((100L - discountRate) / 100L);
    }

    @Override
    public void changeDiscountValue(long discountValue) {
        this.discountRate = discountValue;
    }

    @Override
    public void validateDiscountValue(long discountRate) {
        if (discountRate <= MIN_VOUCHER_RATE) throw new IllegalArgumentException(String.format("Amount should more than %d", MIN_VOUCHER_RATE));
        if (discountRate > MAX_VOUCHER_RATE) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_RATE));
    }
}
