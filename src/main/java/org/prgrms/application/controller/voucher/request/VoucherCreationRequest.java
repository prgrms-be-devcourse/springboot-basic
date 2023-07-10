package org.prgrms.application.controller.voucher.request;

public class VoucherCreationRequest {

    private String voucherType;
    private long discountAmount;

    public VoucherCreationRequest(String voucherType, long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
}
