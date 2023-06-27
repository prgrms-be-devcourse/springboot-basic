package com.wonu606.vouchermanager.domain;

public class VoucherVO {
    private final String type;
    private final double value;

    public VoucherVO(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
