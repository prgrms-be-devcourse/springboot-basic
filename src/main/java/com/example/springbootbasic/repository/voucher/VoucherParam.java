package com.example.springbootbasic.repository.voucher;

public enum VoucherParam {
VOUCHER_ID("voucherId", "voucher_id"),
VOUCHER_TYPE("voucherType", "voucher_type"),
VOUCHER_DISCOUNT_VALUE("voucherDiscountValue", "voucher_discount_value");

    private final String param;
    private final String column;

    VoucherParam(String param, String column) {
        this.param = param;
        this.column = column;
    }

    public String getParam() {
        return param;
    }

    public String getColumn() {
        return column;
    }
}
