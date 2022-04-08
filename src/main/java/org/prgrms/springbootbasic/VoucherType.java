package org.prgrms.springbootbasic;

public enum VoucherType {
    FIXED, PERCENT;

    public boolean isFixed() {
        return this == FIXED;
    }

    public boolean isPercent() {
        return this == PERCENT;
    }
}
