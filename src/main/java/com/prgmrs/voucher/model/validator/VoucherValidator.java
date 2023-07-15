package com.prgmrs.voucher.model.validator;

import com.prgmrs.voucher.enums.VoucherSelectionType;
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

    public void isAmountValid(VoucherSelectionType type, DiscountValue discountValue) throws WrongRangeFormatException {

        if (type == VoucherSelectionType.FIXED_AMOUNT_VOUCHER
                && (0 >= discountValue.value() || discountValue.value() > voucherProperties.getMaximumFixedAmount())) {
            throw new WrongRangeFormatException("amount should be between 0 to defined limit");
        }

        if (type == VoucherSelectionType.PERCENT_DISCOUNT_VOUCHER
                && (0 >= discountValue.value() || discountValue.value() > 100)) {
            throw new WrongRangeFormatException("percent should be between 1-100");
        }

    }
}
