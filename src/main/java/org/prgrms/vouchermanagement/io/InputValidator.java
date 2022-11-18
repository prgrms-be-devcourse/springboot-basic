package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.exception.validation.NotNumberException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectDiscountAmountException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;
import org.prgrms.vouchermanagement.util.RegexConstant;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    Logger logger = LoggerFactory.getLogger(InputValidator.class);

    protected int validateNumber(String discountAmountInput) {
        if (!discountAmountInput.matches(RegexConstant.NUMBER_REGEX)) {
            logger.error("[ERROR] Not Number Error");
            throw new NotNumberException();
        }
        return Integer.parseInt(discountAmountInput);
    }

    protected int validateDiscountAmount(String voucherTypeNumberInput, int discountAmountInput) {
        if (!VoucherType.isCorrectDiscountAmount(voucherTypeNumberInput, discountAmountInput)) {
            logger.error("[ERROR] Incorrect Range Discount Amount Error");
            throw new InCorrectDiscountAmountException();
        }
        return discountAmountInput;
    }

    protected String validateVoucherType(String voucherTypeNumberInput) {
        if (!VoucherType.isCorrectVoucherType(voucherTypeNumberInput)) {
            logger.error("[ERROR] InCorrect Voucher Type Error");
            throw new InCorrectVoucherTypeException();
        }
        return voucherTypeNumberInput;
    }
}
