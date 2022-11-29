package com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

public class VoucherDto {
    private String voucherId;
    private String voucherType;
    private long discount;

    public VoucherDto(String voucherId, String voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
    }
    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId().toString();
        this.voucherType = voucher.getVoucherType().toString();
        this.discount = voucher.getDiscount();
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}

