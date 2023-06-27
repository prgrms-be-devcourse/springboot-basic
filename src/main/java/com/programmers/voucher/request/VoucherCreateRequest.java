package com.programmers.voucher.request;

import com.programmers.voucher.enumtype.VoucherType;

public class VoucherCreateRequest {
    private final VoucherType voucherType;
    private final long amount;

    public VoucherCreateRequest(VoucherType type, long amount) {
        this.voucherType = type;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
