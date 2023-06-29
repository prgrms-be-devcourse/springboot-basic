package com.prgmrs.voucher.util;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.view.ConsoleViewVoucherCreationEnum;

import java.util.regex.Pattern;

public class InputHandler {
    public boolean isValidIntegerString(String token) {
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(token).matches();
    }

    public long stringToLongConverter(String token) throws WrongRangeFormatException {
        if (isValidIntegerString(token)) {
            return Long.parseLong(token);
        }
        throw new WrongRangeFormatException("typed format is invalid.");
    }

    public void isAmountValid(ConsoleViewVoucherCreationEnum type, long maximumFixedAmount, long value) throws WrongRangeFormatException {
        if(type == ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER
            && (0 > value || value > maximumFixedAmount) ) {
            throw new WrongRangeFormatException("typed amount is invalid.");
        }

        if(type == ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER
            && (0 > value || value > 100)) {
            throw new WrongRangeFormatException("typed percent is invalid.");
        }
    }
}
