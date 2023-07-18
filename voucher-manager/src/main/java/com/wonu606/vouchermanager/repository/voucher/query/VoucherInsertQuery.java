package com.wonu606.vouchermanager.repository.voucher.query;

public class VoucherInsertQuery {

    private final String voucherClassSimpleName;
    private final String voucherId;
    private final Double discountValue;

    public VoucherInsertQuery(String voucherClassSimpleName, String voucherId,
            Double discountValue) {
        this.voucherClassSimpleName = voucherClassSimpleName;
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    public String getVoucherClassSimpleName() {
        return voucherClassSimpleName;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public Double getDiscountValue() {
        return discountValue;
    }
}
