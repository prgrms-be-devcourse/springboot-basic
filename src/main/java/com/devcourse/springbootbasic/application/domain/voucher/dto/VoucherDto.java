package com.devcourse.springbootbasic.application.domain.voucher.dto;

public record VoucherDto(
        VoucherType voucherType,
        double discountValue
) {
}
