package com.prgrms.voucher_manager.voucher.controller;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherDto {

    private UUID voucherId;
    private String type;
    private long value;
    private LocalDate createdAt;

    public VoucherDto() {}

    public VoucherDto(UUID voucherId, String type, long value, LocalDate createdAt) {
        this.voucherId = voucherId;
        this.type = type;
        this.value = value;
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public long getValue() {
        return value;
    }
}
