package org.prgms.vouchermanagement.validator;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class VoucherInputValidator {

    private static final Logger logger = LoggerFactory.getLogger(VoucherInputValidator.class);

    public void checkVoucherTypeInput(String input) {
        if (!input.matches("^[1-2]$")) {
            logger.error("Voucher Type Input Error -> {}", input);
            throw new InputMismatchException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
        }
    }

    public void checkFixedAmount(String amount) {
        if (!amount.matches("^[0-9]+$")) {
            logger.error("FixedAmount Input Error -> {}", amount);
            throw new InputMismatchException(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);
        }
    }

    public void checkPercent(String percent) {
        if (!percent.matches("^[1-9]$|^[0-9][0-9]$")) {
            logger.error("PercentDiscount Input Error -> {}", percent);
            throw new InputMismatchException(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);
        }
    }
}
