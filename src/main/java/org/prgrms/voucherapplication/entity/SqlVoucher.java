package org.prgrms.voucherapplication.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class SqlVoucher {

    private final UUID voucherId;
    private String voucherType;
    private long discountAmount;
    private UUID voucherOwner;
    private boolean isIssued;
    private final LocalDateTime createdAt;
    private LocalDateTime issuedAt;

    public SqlVoucher(UUID voucherId, String voucherType, long discountAmount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.isIssued = false;
        this.createdAt = createdAt;
    }

    public SqlVoucher(UUID voucherId, String voucherType, long discountAmount, UUID voucherOwner, boolean isIssued, LocalDateTime createdAt, LocalDateTime issuedAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.voucherOwner = voucherOwner;
        this.isIssued = isIssued;
        this.createdAt = createdAt;
        this.issuedAt = issuedAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public UUID getVoucherOwner() {
        return voucherOwner;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void issueVoucher(UUID customerId) {
        this.voucherOwner = customerId;
        this.isIssued = true;
        this.issuedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "SqlVoucher{" +
                "voucherId=" + voucherId +
                ", voucherType='" + voucherType + '\'' +
                ", discountAmount=" + discountAmount +
                ", voucherOwner=" + (voucherOwner == null ? "\'Not issued\'" : String.valueOf(voucherOwner)) +
                ", isIssued=" + isIssued +
                ", createdAt=" + createdAt +
                ", issuedAt=" + (issuedAt == null ? "\'Not issued\'" : String.valueOf(issuedAt)) +
                '}';
    }

}
