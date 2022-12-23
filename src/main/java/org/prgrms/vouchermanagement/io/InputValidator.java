package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.exception.validation.AbnormalCustomerValueException;
import org.prgrms.vouchermanagement.exception.validation.NotNumberException;
import org.prgrms.vouchermanagement.exception.voucher.AbnormalUUIDFormatException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectDiscountAmountException;
import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;
import org.prgrms.vouchermanagement.util.RegexPatterns;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {

    Logger logger = LoggerFactory.getLogger(InputValidator.class);

    protected void validateNumber(String discountAmountInput) {
        if (!RegexPatterns.NUMBER_REGEX.isMatch(discountAmountInput)) {
            logger.error("[ERROR] Not Number Error");
            throw new NotNumberException();
        }
    }

    protected void validateDiscountAmount(String voucherTypeNumberInput, int discountAmountInput) {
        if (!VoucherType.isCorrectDiscountAmount(voucherTypeNumberInput, discountAmountInput)) {
            logger.error("[ERROR] Incorrect Range Discount Amount Error");
            throw new InCorrectDiscountAmountException();
        }
    }

    protected void validateVoucherType(String voucherTypeNumberInput) {
        if (!VoucherType.isCorrectVoucherType(voucherTypeNumberInput)) {
            logger.error("[ERROR] InCorrect Voucher Type Error");
            throw new InCorrectVoucherTypeException();
        }
    }

    protected void validateName(String customerName) {
        if (!RegexPatterns.NAME_REGEX.isMatch(customerName)) {
            logger.error("[ERROR] Abnormal Name Format Error");
            throw new AbnormalCustomerValueException();
        }
    }

    protected void validateEmail(String customerEmail) {
        if (!RegexPatterns.EMAIL_REGEX.isMatch(customerEmail)) {
            logger.error("[ERROR] Abnormal Email Format Error");
            throw new AbnormalCustomerValueException();
        }
    }

    protected void validateUUID(String uuid) {
        if (!RegexPatterns.UUID_REGEX.isMatch(uuid)) {
            logger.error("[ERROR] Abnormal UUID Format Error");
            throw new AbnormalUUIDFormatException();
        }
    }
}
