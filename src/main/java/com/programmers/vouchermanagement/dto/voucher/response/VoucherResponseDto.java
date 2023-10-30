package com.programmers.vouchermanagement.dto.voucher.response;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;

import java.util.UUID;

public record VoucherResponseDto(UUID id, VoucherType type, long amount) {

    public static VoucherResponseDto from(UUID id, VoucherType type, long amount) {
        return new VoucherResponseDto(id, type, amount);
    }
}
