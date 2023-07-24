package com.prgmrs.voucher.model.wrapper;

import com.prgmrs.voucher.exception.WrongRangeFormatException;

public record Username(String value) {
    public Username {
        if (!value.matches("^[a-zA-Z]+$")) { // "\\d+" matches a sequence of one or more digits
            throw new WrongRangeFormatException("username must contain only alphabets");
        }

        if (value.length() > 255) {
            throw new WrongRangeFormatException("length must be equal to or less than 255");
        }
    }
}
