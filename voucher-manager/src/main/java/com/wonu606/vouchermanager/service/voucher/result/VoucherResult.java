package com.wonu606.vouchermanager.service.voucher.result;

public class VoucherResult {

    private final String uuid;
    private final String voucherClassType;
    private final Double discountValue;

    public VoucherResult(String uuid, String voucherClassType, Double discountValue) {
        this.uuid = uuid;
        this.voucherClassType = voucherClassType;
        this.discountValue = discountValue;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVoucherClassType() {
        return voucherClassType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
