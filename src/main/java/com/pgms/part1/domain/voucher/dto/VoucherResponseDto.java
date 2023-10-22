package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;

import java.util.UUID;

public record VoucherResponseDto(UUID id, Integer discount, VoucherDiscountType voucherDiscountType) {
}
