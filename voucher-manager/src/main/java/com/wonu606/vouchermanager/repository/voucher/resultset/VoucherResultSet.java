package com.wonu606.vouchermanager.repository.voucher.resultset;

public class VoucherResultSet {

    private final String uuid;
    private final String voucherClassType;
    private final Double discountValue;

    public VoucherResultSet(String uuid, String voucherClassType, Double discountValue) {
        this.voucherClassType = voucherClassType;
        this.uuid = uuid;
        this.discountValue = discountValue;
    }

    public String getVoucherClassType() {
        return voucherClassType;
    }

    public String getUuid() {
        return uuid;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
