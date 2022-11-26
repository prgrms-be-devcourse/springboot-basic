package com.example.springbootbasic.domain.voucher;

import java.time.LocalDateTime;

import static com.example.springbootbasic.exception.voucher.VoucherExceptionMessage.*;

public abstract class Voucher {

    private final Long voucherId;
    private final Long discountValue;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    Voucher(Long discountValue, VoucherType voucherType, LocalDateTime createdAt, LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
        validateDiscountValue(discountValue);
        validateVoucherType(voucherType);
        this.voucherId = 0L;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    Voucher(Long voucherId, Long discountValue, VoucherType voucherType, LocalDateTime localDateTime, LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
        validateVoucherId(voucherId);
        validateDiscountValue(discountValue);
        validateVoucherType(voucherType);
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = localDateTime;
    }

    private void validateVoucherType(VoucherType voucherType) {
        if (voucherType == null) {
            throw new IllegalArgumentException(WRONG_VOUCHER_ID_EXCEPTION.getMessage());
        }
    }

    private void validateDiscountValue(Long discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException(WRONG_VOUCHER_DISCOUNT_VALUE_EXCEPTION.getMessage());
        }

    }

    private void validateVoucherId(Long voucherId) {
        if (voucherId < 0) {
            throw new IllegalArgumentException(WRONG_VOUCHER_TYPE_EXCEPTION.getMessage());
        }
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() { return voucherType; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", discountValue=" + discountValue +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                '}';
    }
}
