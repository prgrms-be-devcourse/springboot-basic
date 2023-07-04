package com.programmers.domain.voucher.dto;

import com.programmers.domain.voucher.VoucherType;

import java.util.UUID;

public record VoucherUpdateRequestDto(UUID id, String name, long amount, VoucherType type) {

}
