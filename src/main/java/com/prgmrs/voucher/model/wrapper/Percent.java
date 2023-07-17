package com.prgmrs.voucher.model.wrapper;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public record Percent(long value) {
    public Percent {
        if (value <= 0 || value > 100) {
            throw new WrongRangeFormatException("Percent value must be between 1 and 100.");
        }
    }

}