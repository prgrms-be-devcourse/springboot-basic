package com.dojinyou.devcourse.voucherapplication.voucher.dto;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public class VoucherResponseDto {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;

    public VoucherResponseDto(Long id, VoucherType type, VoucherAmount amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "Voucher ID : " + id + System.lineSeparator() +
                "Voucher Type : " + type + System.lineSeparator() +
                "Voucher Amount : " + amount.getAmount() + System.lineSeparator();
    }

    public Long getVoucherId() {
        return this.id;
    }

    public VoucherType getVoucherType() {
        return this.type;
    }

    public VoucherAmount getVoucherAmount() {
        return this.amount;
    }
}
