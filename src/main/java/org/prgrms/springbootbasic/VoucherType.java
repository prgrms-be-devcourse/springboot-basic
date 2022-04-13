package org.prgrms.springbootbasic;

public enum VoucherType {
    FIXED("fixed"), PERCENT("percent");

    private final String textType;

    VoucherType(final String textType) {
        this.textType = textType;
    }

    public boolean isFixed() {
        return this == FIXED;
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    @Override
    public String toString() {
        return textType;
    }
}
