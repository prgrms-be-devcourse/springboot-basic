package com.prgmrs.voucher.model.wrapper;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public record Username(String value) {
    public Username {
        if (value == null) {
            throw new WrongRangeFormatException("input value null");
        }

        if (!value.matches("^[a-zA-Z]+$")) { // "\\d+" matches a sequence of one or more digits
            throw new WrongRangeFormatException("username must contain only alphabets");
        }

        if (value.length() < 1 || value.length() > 255) {
            throw new WrongRangeFormatException("length must be between 1 to 255");
        }
    }
}
