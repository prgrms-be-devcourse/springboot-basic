package com.prgrms.vouchermanagement.core.voucher.controller.response;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public class VoucherResponse {

    private String id;
    private String name;
    private long amount;
    private VoucherType voucherType;

    public VoucherResponse() {
    }

    public VoucherResponse(String id, String name, long amount, VoucherType voucherType) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
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
