package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.exception.validation.NotNumberException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectDiscountAmountException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;
import org.prgrms.vouchermanagement.util.RegexConstant;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    protected int validateNumber(String discountAmountInput) {
        if (!discountAmountInput.matches(RegexConstant.NUMBER_REGEX)) {
            throw new NotNumberException();
        }
        return Integer.parseInt(discountAmountInput);
    }

    protected int validateDiscountAmount(String voucherTypeNumberInput, int discountAmountInput) {
        if (!VoucherType.isCorrectDiscountAmount(voucherTypeNumberInput, discountAmountInput)) {
            throw new InCorrectDiscountAmountException();
        }
        return discountAmountInput;
    }

    protected String validateVoucherType(String voucherTypeNumberInput) {
        if (!VoucherType.isCorrectVoucherType(voucherTypeNumberInput)) {
            throw new InCorrectVoucherTypeException();
        }
        return voucherTypeNumberInput;
    }
}
