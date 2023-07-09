package org.prgrms.application.controller.voucher.request;

public class VoucherCreationRequest {

    private String voucherType;
    private String discountDetail;

    public VoucherCreationRequest(String voucherType, String discountDetail) {
        this.voucherType = voucherType;
        this.discountDetail = discountDetail;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }
}
