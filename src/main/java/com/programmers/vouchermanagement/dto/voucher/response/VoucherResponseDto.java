package com.programmers.vouchermanagement.dto.voucher.response;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import lombok.Data;

import java.util.UUID;

@Data
public class VoucherResponseDto {
    private UUID id;
    private VoucherType type;
    private long amount;

    private VoucherResponseDto(UUID id, VoucherType type, long amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static VoucherResponseDto from(UUID id, VoucherType type, long amount) {
        return new VoucherResponseDto(id, type, amount);
    }
}
