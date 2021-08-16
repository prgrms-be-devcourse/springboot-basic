package org.prgrms.kdt.dto;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.UUID;

public class VoucherRequestDto {

    private int voucherType;
    // amount or percentage
    private long discount;

    public int getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
