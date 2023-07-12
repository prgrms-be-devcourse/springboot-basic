package com.programmers.voucher.dto;

import com.programmers.voucher.domain.DiscountType;

public record VoucherRequestDto(DiscountType discountType, long value) {
}
