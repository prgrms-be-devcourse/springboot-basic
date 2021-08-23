package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

public class VoucherRequestDto {

    private VoucherType voucherType;
    // amount or percentage
    private long discount;

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
