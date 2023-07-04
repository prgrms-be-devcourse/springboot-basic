package com.programmers.domain.voucher.dto;

import com.programmers.domain.voucher.VoucherType;

import java.util.UUID;

public record VoucherResponseDto(UUID id, String name, long value, VoucherType type) {

}
