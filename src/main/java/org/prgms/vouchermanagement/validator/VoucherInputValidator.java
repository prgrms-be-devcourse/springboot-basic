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
}
