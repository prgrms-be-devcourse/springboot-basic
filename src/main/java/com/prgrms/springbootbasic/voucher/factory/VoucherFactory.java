package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.Voucher;

import java.text.MessageFormat;

public interface VoucherFactory {

    default Voucher requestVoucher(VoucherType voucherType, String discountAmountInput) {
        validate(discountAmountInput);
        int discountAmount = parseAmount(discountAmountInput);
        return createVoucher(voucherType, discountAmount);
    }

    default void validate(String amountInput) {
        try {
            Integer.parseInt(amountInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    MessageFormat.format("Your input {0} is not integer.", amountInput));
        }
    }

    default int parseAmount(String discountAmount) {
        return Integer.parseInt(discountAmount);
    }

    Voucher createVoucher(VoucherType voucherType, int discountAmount);

    VoucherType getType();
}
