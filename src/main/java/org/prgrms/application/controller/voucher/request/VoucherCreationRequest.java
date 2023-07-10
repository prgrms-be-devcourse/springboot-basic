package org.prgrms.application.controller.voucher.request;

public class VoucherCreationRequest {

    private String voucherType;
    private String discountAmount;

    public VoucherCreationRequest(String voucherType, String discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }
}
