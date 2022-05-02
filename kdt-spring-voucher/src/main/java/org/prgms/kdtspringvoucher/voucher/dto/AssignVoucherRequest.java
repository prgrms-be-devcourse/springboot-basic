package org.prgms.kdtspringvoucher.voucher.dto;

import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class AssignVoucherRequest {

    private UUID voucherId;
    private UUID customerId;
    private Long amount;
    private VoucherType voucherType;
    private LocalDateTime createdAt;

    public AssignVoucherRequest(UUID voucherId, UUID customerId, Long amount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.amount = amount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

