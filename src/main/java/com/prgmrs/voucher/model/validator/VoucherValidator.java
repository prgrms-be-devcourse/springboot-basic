package com.prgmrs.voucher.model.validator;

import com.prgmrs.voucher.enums.DiscountType;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.VoucherProperties;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VoucherValidator {
    private final VoucherProperties voucherProperties;

    public VoucherValidator(VoucherProperties voucherProperties) {
        this.voucherProperties = voucherProperties;
    }

    public Long convertToLongWithValidation(String discountStringValue, DiscountType discountType) {
        isValidIntegerString(discountStringValue);
        Long convertedValue = Long.parseLong(discountStringValue);
        isAmountValid(convertedValue, discountType);
        return convertedValue;
    }

    private void isValidIntegerString(String discountStringValue) {
        Pattern pattern = Pattern.compile("^[-+]?\\d+$");
        if (!pattern.matcher(discountStringValue).matches()) {
            throw new WrongRangeFormatException("only digit are allowed");
        }
    }

    private void isAmountValid(long discountValue, DiscountType discountType) {
        if (discountType == DiscountType.FIXED_AMOUNT_DISCOUNT
                && (0 >= discountValue || discountValue > voucherProperties.getMaximumFixedAmount())) {
            throw new WrongRangeFormatException("amount should be between 0 to defined limit");
        }

        if (discountType == DiscountType.PERCENT_DISCOUNT
                && (0 >= discountValue || discountValue > 100)) {
            throw new WrongRangeFormatException("percent should be between 1-100");
        }
    }
}
