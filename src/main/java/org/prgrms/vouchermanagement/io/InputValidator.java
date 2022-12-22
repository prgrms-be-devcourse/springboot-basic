package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.exception.validation.AbnormalCustomerValueException;
import org.prgrms.vouchermanagement.exception.validation.NotNumberException;
import org.prgrms.vouchermanagement.exception.voucher.AbnormalUUIDFormatException;
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
        if (!discountAmountInput.matches(RegexConstant.NUMBER_REGEX.getRegex())) {
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

    protected String validateName(String customerName) {
        if (!customerName.matches(RegexConstant.NAME_REGEX.getRegex())) {
            logger.error("[ERROR] Abnormal Name Format Error");
            throw new AbnormalCustomerValueException();
        }
        return customerName;
    }

    protected String validateEmail(String customerEmail) {
        if (!customerEmail.matches(RegexConstant.EMAIL_REGEX.getRegex())) {
            logger.error("[ERROR] Abnormal Email Format Error");
            throw new AbnormalCustomerValueException();
        }
        return customerEmail;
    }

    protected String validateUUID(String uuid) {
        if (!uuid.matches(RegexConstant.UUID_REGEX.getRegex())) {
            logger.error("[ERROR] Abnormal UUID Format Error");
            throw new AbnormalUUIDFormatException();
        }
        return uuid;
    }
}
