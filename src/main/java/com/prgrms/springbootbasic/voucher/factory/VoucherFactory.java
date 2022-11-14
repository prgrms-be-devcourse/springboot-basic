package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public interface VoucherFactory {

    Logger logger = LoggerFactory.getLogger(VoucherFactory.class);

    default Voucher requestVoucher(String discountAmountInput) {
        validate(discountAmountInput);
        int discountAmount = parseAmount(discountAmountInput);
        return createVoucher(discountAmount);
    }

    default void validate(String amountInput) {
        try {
            Integer.parseInt(amountInput);
        } catch (NumberFormatException e) {
            logger.warn("NumberFormatException occurred when parsing amount input. Cannot parse String to Integer");
            throw new NumberFormatException(
                    MessageFormat.format("Your input {0} is not integer.", amountInput));
        }
    }

    default int parseAmount(String discountAmount) {
        return Integer.parseInt(discountAmount);
    }

    Voucher createVoucher(int discountAmount);

    VoucherType getType();
}
