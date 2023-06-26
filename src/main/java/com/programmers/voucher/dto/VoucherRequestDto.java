package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Discount;

import java.util.UUID;

public record VoucherRequestDto(UUID voucherId, Discount discount) {
}
