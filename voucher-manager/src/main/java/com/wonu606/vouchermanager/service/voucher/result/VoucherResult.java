package com.wonu606.vouchermanager.service.voucher.result;

public class VoucherResult {

    private final String voucherUuid;
    private final String voucherClassType;
    private final Double discountValue;

    public VoucherResult(String voucherUuid, String voucherClassType, Double discountValue) {
        this.voucherUuid = voucherUuid;
        this.voucherClassType = voucherClassType;
        this.discountValue = discountValue;
    }

    public String getVoucherUuid() {
        return voucherUuid;
    }

    public String getVoucherClassType() {
        return voucherClassType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
