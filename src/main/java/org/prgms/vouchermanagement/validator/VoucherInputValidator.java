package org.prgms.vouchermanagement.validator;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class VoucherInputValidator {

    public void checkVoucherTypeInput(String input) {
        if (!input.matches("^[1-2]$")) {
            throw new InputMismatchException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
        }
    }

    public void checkFixedAmount(String amount) {
        if (!amount.matches("^[0-9]+$")) {
            throw new InputMismatchException(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);
        }
    }

    public void checkPercent(String percent) {
        if (!percent.matches("^[1-9]$|^[0-9][0-9]$")) {
            throw new InputMismatchException(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);
        }
    }
}
