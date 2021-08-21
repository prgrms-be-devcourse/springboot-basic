package org.prgrms.kdt.dto;

import org.prgrms.kdt.enums.VoucherType;

public class VoucherSaveRequestDto {

    private VoucherType voucherType;
    private long discount;

    public VoucherSaveRequestDto(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

}