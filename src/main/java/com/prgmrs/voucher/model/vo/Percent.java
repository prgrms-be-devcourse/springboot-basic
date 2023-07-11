package com.prgmrs.voucher.model.vo;

public record Percent(long value) {
    public Percent {
        if (value <= 0 || value > 100) {
            throw new IllegalArgumentException("Percent value must be between 1 and 100.");
        }
    }

}