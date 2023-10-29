package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;

public record VoucherResponseDto(Long id, Integer discount, VoucherDiscountType voucherDiscountType) {
}
