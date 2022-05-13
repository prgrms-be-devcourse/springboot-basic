package org.prgrms.springbootbasic.controller;

public enum VoucherType {
    FIXED,
    PERCENT;

    public boolean isFixed() {
        return this == FIXED;
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
