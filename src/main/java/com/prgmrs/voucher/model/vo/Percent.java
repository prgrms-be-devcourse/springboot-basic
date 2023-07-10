package com.prgmrs.voucher.model.vo;

public class Percent {
    private final long value;

    public Percent(long value) {
        if (value <= 0 || value > 100) {
            throw new IllegalArgumentException("Percent value must be between 1 and 100.");
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}