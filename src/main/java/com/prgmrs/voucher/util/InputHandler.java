package com.prgmrs.voucher.util;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.VoucherProperties;
import com.prgmrs.voucher.view.ConsoleViewVoucherCreationEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class InputHandler {
    private final VoucherProperties voucherProperties;

    public InputHandler(VoucherProperties voucherProperties) {
        this.voucherProperties = voucherProperties;
    }

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

    public boolean isAmountValid(ConsoleViewVoucherCreationEnum type, long value) throws WrongRangeFormatException {
        boolean isValid = true;

        if (type == ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER
                && (0 > value || value > voucherProperties.getMaximumFixedAmount())) {
            isValid = false;
        }

        if (type == ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER
                && (0 > value || value > 100)) {
            isValid = false;
        }

        return isValid;
    }
}
