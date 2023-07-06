package com.programmers.voucher.dto;

import com.programmers.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherResponseDto(UUID id, String name, long value, VoucherType type) {

}
