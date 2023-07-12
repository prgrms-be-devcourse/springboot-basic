package org.prgms.vouchermanagement.voucher.validator;

import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VoucherInputValidator {

    private static final Pattern INTEGER_ONE_OR_TWO = Pattern.compile("^[1-2]$");
    private static final Pattern INTEGER_NO_BOUNDARY = Pattern.compile("^[0-9]+$");
    private static final Pattern INTEGER_ONE_TO_NINETY_NINE = Pattern.compile("^[1-9]$|^[0-9][0-9]$");

    public void checkVoucherTypeInput(String input) {
        if (!INTEGER_ONE_OR_TWO.matcher(input).matches()) {
            throw new VoucherException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
        }
    }

    public void checkFixedAmount(String amount) {
        if (!INTEGER_NO_BOUNDARY.matcher(amount).matches()) {
            throw new VoucherException(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);
        }
    }

    public void checkPercent(String percent) {
        if (!INTEGER_ONE_TO_NINETY_NINE.matcher(percent).matches()) {
            throw new VoucherException(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);
        }
    }
}
