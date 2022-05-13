package org.prgrms.voucherprgrms.voucher.model;

public class VoucherForm {
    private String voucherType;
    private long value;

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getValue() {
        return value;
    }

    public VoucherForm(){}

    public VoucherForm(String voucherType, long value) {
        this.voucherType = voucherType;
        this.value = value;
    }
}
