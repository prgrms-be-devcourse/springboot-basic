package kdt.vouchermanagement.domain.voucher.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Voucher implements Serializable {
    private final Long voucherId;
    private final VoucherType voucherType;
    private final int discountValue;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Voucher(VoucherType voucherType, int discountValue) {
        this.voucherId = null;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Voucher(Long voucherId, VoucherType voucherType, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", voucherType=" + voucherType +
                ", discountValue=" + discountValue +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public abstract void validateValueRange();
}