package org.prgrms.dev.voucher.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class InsertVoucherDto {
    private UUID voucherId;
    private String voucherType;
    private long discount;
    private LocalDateTime createdAt;

    public InsertVoucherDto(String voucherType, long discount) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discount = discount;
        this.createdAt = LocalDateTime.now();
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
