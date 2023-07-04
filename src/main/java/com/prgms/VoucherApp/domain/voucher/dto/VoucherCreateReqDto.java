package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.math.BigDecimal;

public class VoucherCreateReqDto {
    private VoucherType voucherType;
    private BigDecimal amount;

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
