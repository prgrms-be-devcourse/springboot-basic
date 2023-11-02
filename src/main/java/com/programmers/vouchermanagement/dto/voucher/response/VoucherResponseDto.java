package com.programmers.vouchermanagement.dto.voucher.response;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VoucherResponseDto {
    private UUID id;
    private VoucherType type;
    private long amount;
    private LocalDateTime createdAt;

    private VoucherResponseDto(UUID id, VoucherType type, long amount, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static VoucherResponseDto from(UUID id, VoucherType type, long amount, LocalDateTime createdAt) {
        return new VoucherResponseDto(id, type, amount, createdAt);
    }
}
