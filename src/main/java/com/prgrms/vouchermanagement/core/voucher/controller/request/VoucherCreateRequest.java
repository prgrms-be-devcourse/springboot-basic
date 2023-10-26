package com.prgrms.vouchermanagement.core.voucher.controller.request;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public class VoucherCreateRequest {

    private final String name;
    private final VoucherType voucherType;
    private final long amount;

    public VoucherCreateRequest(String name, VoucherType voucherType, long amount) {
        this.name = name;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
