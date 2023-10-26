package org.prgms.kdtspringweek1.voucher.entity;

public enum VoucherType {
    FIXED_AMOUNT("fixed amount voucher", "amount"),
    PERCENT_DISCOUNT("percent discount voucher", "percent");

    private String name;
    private String unit;

    VoucherType(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}