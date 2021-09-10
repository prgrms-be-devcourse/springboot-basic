package org.prgrms.kdt.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseVoucherDto {

    private UUID voucherId;
    private long value;
    private VoucherType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ResponseVoucherDto(UUID voucherId, long value, VoucherType type, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
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
