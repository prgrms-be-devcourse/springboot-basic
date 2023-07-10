package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;

public class VoucherCreateReqDto {
    private final VoucherType voucherType;
    private final BigDecimal amount;

    public VoucherCreateReqDto(VoucherType voucherType, BigDecimal amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
