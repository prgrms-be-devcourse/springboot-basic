package org.prgrms.springbootbasic.service;

public enum VoucherType {
    FIXED, PERCENT;

    public boolean isFixed() {
        return this == FIXED;
    }

    public boolean isPercent() {
        return this == PERCENT;
    }
}
