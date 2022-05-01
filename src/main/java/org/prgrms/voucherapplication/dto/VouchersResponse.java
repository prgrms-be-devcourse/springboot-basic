package org.prgrms.voucherapplication.dto;

import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.view.io.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VouchersResponse {
    private final UUID voucherId;
    private final String voucherType;
    private final long discountAmount;
    private final UUID voucherOwner;
    private final boolean isIssued;
    private final LocalDateTime createdAt;
    private final LocalDateTime issuedAt;

    public VouchersResponse(UUID voucherId, VoucherType voucherType, long discountAmount, UUID voucherOwner, boolean isIssued, LocalDateTime createdAt, LocalDateTime issuedAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType.name();
        this.discountAmount = discountAmount;
        this.voucherOwner = voucherOwner;
        this.isIssued = isIssued;
        this.createdAt = createdAt;
        this.issuedAt = issuedAt;
    }

    public VouchersResponse(SqlVoucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = voucher.getVoucherType();
        this.discountAmount = voucher.getDiscountAmount();
        this.voucherOwner = voucher.getVoucherOwner();
        this.isIssued = voucher.isIssued();
        this.createdAt = voucher.getCreatedAt();
        this.issuedAt = voucher.getIssuedAt();
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
}
