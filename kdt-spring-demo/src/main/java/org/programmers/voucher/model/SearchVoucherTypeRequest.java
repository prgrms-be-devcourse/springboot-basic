package org.programmers.voucher.model;

public class SearchVoucherTypeRequest {
    private VoucherType voucherType;

    public SearchVoucherTypeRequest(){}

    public SearchVoucherTypeRequest(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }
}
