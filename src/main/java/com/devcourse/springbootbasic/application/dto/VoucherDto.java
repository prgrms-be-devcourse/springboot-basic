package com.devcourse.springbootbasic.application.dto;

public record VoucherDto(
        VoucherType voucherType,
        DiscountValue discountValue
) {
}
