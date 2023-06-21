package com.devmission.springbootbasic.voucher;

public class VoucherRequest {

    private final VoucherType voucherType;
    private final int value;

    public VoucherRequest(VoucherType voucherType, int value) {
        this.voucherType = voucherType;
        this.value = value;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getValue() {
        return value;
    }

}
