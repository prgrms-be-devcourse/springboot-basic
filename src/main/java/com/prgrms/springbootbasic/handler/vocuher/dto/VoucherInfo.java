package com.prgrms.springbootbasic.handler.vocuher.dto;

import com.prgrms.springbootbasic.handler.vocuher.VoucherType;

public class VoucherInfo {

    private final VoucherType voucherType;
    private final int amount;

    public VoucherInfo(VoucherType voucherType, int amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }
}
