package com.prgrms.vouchermanagement.core.voucher.controller.response;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public class VoucherResponse {

    private final long id;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;

    public VoucherResponse(long id, String name, long amount, VoucherType voucherType) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    @Override
    public String toString() {
        return "Voucher = {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                '}';
    }
}
