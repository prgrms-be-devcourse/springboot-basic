package com.prgmrs.voucher.model.vo;

public class Amount {
    private final long value;

    public Amount(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Percent value must be positive integer.");
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
