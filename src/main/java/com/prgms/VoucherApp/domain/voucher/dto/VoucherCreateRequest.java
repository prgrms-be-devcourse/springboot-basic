package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;

public record VoucherCreateRequest(
        VoucherType voucherType,
        BigDecimal amount
) {
    public VoucherCreateRequest() {
        this(VoucherType.PERCENT_VOUCHER, BigDecimal.valueOf(0));
    }
}
