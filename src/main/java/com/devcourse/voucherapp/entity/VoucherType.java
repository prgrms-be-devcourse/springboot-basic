package com.devcourse.voucherapp.entity;

public enum VoucherType {
    FIX("1", "고정 할인"),
    PERCENT("2", "비율 할인");

    private final String number;
    private final String name;

    VoucherType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }
}
