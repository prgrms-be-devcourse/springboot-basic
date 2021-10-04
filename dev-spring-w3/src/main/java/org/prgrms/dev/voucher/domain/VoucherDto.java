package org.prgrms.dev.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private String voucherType;
    private long discount;
    private LocalDateTime createdAt;

    // for insert
    public VoucherDto(UUID voucherId, String voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
        this.createdAt = LocalDateTime.now();
    }

    // for update
    public VoucherDto(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
