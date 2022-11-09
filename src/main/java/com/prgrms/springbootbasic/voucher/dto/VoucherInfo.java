package com.prgrms.springbootbasic.voucher.dto;

import com.prgrms.springbootbasic.voucher.VoucherType;

public class VoucherInfo {

    private final VoucherType voucherType;
    private final int amount;

    public VoucherInfo(VoucherType voucherType, int amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }
}
