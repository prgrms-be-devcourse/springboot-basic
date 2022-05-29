package org.prgrms.kdt.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.voucher.model.VoucherType;

public class VoucherResponse {

    private UUID id;
    private long value;
    private VoucherType type;
    private LocalDateTime createdAt;

    public VoucherResponse(UUID id, long value, VoucherType type,
        LocalDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public VoucherType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
