package com.wonu606.vouchermanager.repository.voucher.resultset;

public class VoucherResultSet {

    private final String simpleName;
    private final String uuid;
    private final Double discountValue;

    public VoucherResultSet(String simpleName, String uuid, Double discountValue) {
        this.simpleName = simpleName;
        this.uuid = uuid;
        this.discountValue = discountValue;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getUuid() {
        return uuid;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
