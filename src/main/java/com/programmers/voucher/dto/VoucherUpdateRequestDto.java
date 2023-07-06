package com.programmers.voucher.dto;

import com.programmers.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherUpdateRequestDto(UUID id, String name, long amount, VoucherType type) {

}
