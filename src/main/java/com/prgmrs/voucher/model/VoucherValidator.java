package com.prgmrs.voucher.model;

import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.setting.VoucherProperties;
import com.prgmrs.voucher.enums.ConsoleViewVoucherCreationEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VoucherValidator {
    private final VoucherProperties voucherProperties;

    public VoucherValidator(VoucherProperties voucherProperties) {
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

    public boolean isAmountValid(ConsoleViewVoucherCreationEnum type, DiscountValue discountValue) throws WrongRangeFormatException {
        boolean isValid = true;

        if (type == ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER
                && (0 > discountValue.getValue() || discountValue.getValue() > voucherProperties.getMaximumFixedAmount())) {
            isValid = false;
        }

        if (type == ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER
                && (0 >= discountValue.getValue() || discountValue.getValue() > 100)) {
            isValid = false;
        }

        return isValid;
    }
}
