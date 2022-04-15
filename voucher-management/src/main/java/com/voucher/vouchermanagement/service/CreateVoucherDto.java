package com.voucher.vouchermanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateVoucherDto {
    private final UUID id;

    public UUID getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private final long value;
    private final LocalDateTime createdAt;

    public CreateVoucherDto(UUID id, long value, LocalDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public CreateVoucherDto(long value) {
        this(UUID.randomUUID(), value, LocalDateTime.now());
    }

}
