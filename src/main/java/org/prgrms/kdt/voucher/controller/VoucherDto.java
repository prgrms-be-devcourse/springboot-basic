package org.prgrms.kdt.voucher.controller;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private long discount;
    private LocalDateTime createdAt;
    private String voucherType;

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }
}
