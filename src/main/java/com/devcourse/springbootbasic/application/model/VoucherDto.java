package com.devcourse.springbootbasic.application.model;

public record VoucherDto(
        VoucherType voucherType,
        DiscountValue discountValue
) {
}
