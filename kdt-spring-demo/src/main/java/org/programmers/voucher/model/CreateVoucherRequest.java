package org.programmers.voucher.model;

public class CreateVoucherRequest {
    private VoucherType voucherType;
    private Integer voucherValue;

    public CreateVoucherRequest(){}

    public CreateVoucherRequest(VoucherType voucherType, int voucherValue) {
        this.voucherType = voucherType;
        this.voucherValue = voucherValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherType(VoucherType voucherType){
        this.voucherType = voucherType;
    }

    public void setVoucherValue(Integer voucherValue) {
        this.voucherValue = voucherValue;
    }
}
