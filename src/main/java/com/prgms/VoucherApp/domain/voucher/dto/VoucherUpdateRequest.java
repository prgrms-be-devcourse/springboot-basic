package com.prgms.VoucherApp.domain.voucher.dto;

import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherUpdateReqDto {

    private final UUID id;
    private final BigDecimal amount;

    private VoucherType voucherType;

    public VoucherUpdateReqDto(UUID id, BigDecimal amount, VoucherType voucherType) {
        this.id = id;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
