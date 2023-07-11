package com.prgmrs.voucher.model.vo;

public record Amount(long value) {
    public Amount {
        if (value <= 0) {
            throw new IllegalArgumentException("Percent value must be positive integer.");
        }
    }
}
