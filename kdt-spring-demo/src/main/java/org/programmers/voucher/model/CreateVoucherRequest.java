package org.programmers.voucher.model;

public class CreateVoucherRequest {
    private final VoucherType voucherType;
    private final long voucherValue;


    public CreateVoucherRequest(VoucherType voucherType, long voucherValue) {
        this.voucherType = voucherType;
        this.voucherValue = voucherValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getVoucherValue() {
        return voucherValue;
    }
}
