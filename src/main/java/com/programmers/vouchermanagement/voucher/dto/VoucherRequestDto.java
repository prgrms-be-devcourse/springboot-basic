package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherRequestDto {

    private final VoucherType voucherType;
    private final Long discount;

    public VoucherRequestDto(VoucherType voucherType, Long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscount() {
        return discount;
    }
}
