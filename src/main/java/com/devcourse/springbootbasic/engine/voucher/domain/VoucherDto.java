package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

public record VoucherDto(
        VoucherType voucherType,
        double discountValue
) {
}
