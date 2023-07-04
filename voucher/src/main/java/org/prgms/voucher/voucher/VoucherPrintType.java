package org.prgms.voucher.voucher;

public enum VoucherPrintType {
    VOUCHER_TYPE("voucher_type"),
    AFTER_DISCOUNT_VALUE("after_discount_value"),
    PUBLISH_DATE("publish_date"),
    EXPIRATION_DATE("expiration_date");

    private final String type;

    VoucherPrintType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
