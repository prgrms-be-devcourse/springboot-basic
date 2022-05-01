package org.prgrms.springbootbasic.controller;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String textName;

    VoucherType(final String textName) {
        this.textName = textName;
    }

    public boolean isFixed() {
        return this == FIXED;
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    @Override
    public String toString() {
        return textName;
    }
}
