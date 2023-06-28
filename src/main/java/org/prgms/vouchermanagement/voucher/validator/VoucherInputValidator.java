package org.prgms.vouchermanagement.voucher.validator;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.regex.Pattern;

@Component
public class VoucherInputValidator {

    private static final Logger logger = LoggerFactory.getLogger(VoucherInputValidator.class);
    private static final Pattern INTEGER_ONE_OR_TWO = Pattern.compile("^[1-2]$");
    private static final Pattern INTEGER_NO_BOUNDARY = Pattern.compile("^[0-9]+$");
    private static final Pattern INTEGER_ONE_TO_NINETY_NINE = Pattern.compile("^[1-9]$|^[0-9][0-9]$");

    public void checkVoucherTypeInput(String input) {
        if (!INTEGER_ONE_OR_TWO.matcher(input).matches()) {
            logger.error("Voucher Type Input Error -> {}", input);
            throw new InputMismatchException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
        }
    }

    public void checkFixedAmount(String amount) {
        if (!INTEGER_NO_BOUNDARY.matcher(amount).matches()) {
            logger.error("FixedAmount Input Error -> {}", amount);
            throw new InputMismatchException(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);
        }
    }

    public void checkPercent(String percent) {
        if (!INTEGER_ONE_TO_NINETY_NINE.matcher(percent).matches()) {
            logger.error("PercentDiscount Input Error -> {}", percent);
            throw new InputMismatchException(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);
        }
    }
}
