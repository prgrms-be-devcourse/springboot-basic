package com.pgms.part1.domain.voucher.dto;

import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;

public record VoucherWebCreateRequestDto (Integer discount, VoucherDiscountType voucherDiscountType) {
}
