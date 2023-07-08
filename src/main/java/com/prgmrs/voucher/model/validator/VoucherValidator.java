package com.prgmrs.voucher.model.validator;

import com.prgmrs.voucher.enums.VoucherType;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.setting.VoucherProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class VoucherValidator {
    private final VoucherProperties voucherProperties;

    public VoucherValidator(VoucherProperties voucherProperties) {
        this.voucherProperties = voucherProperties;
    }

    public boolean isValidIntegerString(String token) {
        Pattern pattern = Pattern.compile("^[-+]?\\d+$");
        return pattern.matcher(token).matches();
    }

    public Optional<Long> stringToLongConverter(String token) throws WrongRangeFormatException {
        if (isValidIntegerString(token)) {
            return Optional.of(Long.parseLong(token));
        }
        return Optional.empty();
    }

    public boolean isAmountValid(VoucherType type, DiscountValue discountValue) throws WrongRangeFormatException {
        boolean isValid = true;

        if (type == VoucherType.FIXED_AMOUNT_VOUCHER
                && (0 >= discountValue.getValue() || discountValue.getValue() > voucherProperties.getMaximumFixedAmount())) {
            isValid = false;
        }

        if (type == VoucherType.PERCENT_DISCOUNT_VOUCHER
                && (0 >= discountValue.getValue() || discountValue.getValue() > 100)) {
            isValid = false;
        }

        return isValid;
    }
}
