package com.prgmrs.voucher.model.wrapper;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public record Amount(long value) {
    public Amount {
        if (value <= 0) {
            throw new WrongRangeFormatException("Percent value must be positive integer.");
        }
    }
}
