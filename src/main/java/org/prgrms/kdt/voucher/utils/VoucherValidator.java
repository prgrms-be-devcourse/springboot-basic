package org.prgrms.kdt.voucher.utils;

import org.prgrms.kdt.voucher.VoucherAmount;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class VoucherValidator {
    private static final Long FIXED_AMOUNT_VOUCHER_MIN_VALUE = 0L;
    private static final Long PERCENT_DISCOUNT_VOUCHER_MIN_VALUE = 0L;
    private static final Long PERCENT_DISCOUNT_VOUCHER_MAX_VALUE = 100L;

    public void validateAmount(VoucherType voucherType, VoucherAmount voucherAmount) {
        switch (voucherType) {
            case FIXED -> validateFixedAmount(voucherAmount);
            case PERCENT -> validatePercentDiscount(voucherAmount);
        }
    }

    private void validatePercentDiscount(VoucherAmount voucherAmount) {
        if (!isValidValueOfPercentDiscountVoucher(voucherAmount)) {
            throw new NumberFormatException("Please enter a value between " + PERCENT_DISCOUNT_VOUCHER_MIN_VALUE + " and " + PERCENT_DISCOUNT_VOUCHER_MAX_VALUE + "." + System.lineSeparator());
        }
    }

    private void validateFixedAmount(VoucherAmount voucherAmount) {
        if (!isValidValueOfFixedAmountVoucher(voucherAmount)) {
            throw new NumberFormatException("Please enter a value greater than or equal " + FIXED_AMOUNT_VOUCHER_MIN_VALUE  + "." + System.lineSeparator());
        }
    }

    public boolean isValidValueOfPercentDiscountVoucher(VoucherAmount voucherAmount) {
        return voucherAmount.isGreaterThanEqual(PERCENT_DISCOUNT_VOUCHER_MIN_VALUE) && voucherAmount.isLessThanEqual(PERCENT_DISCOUNT_VOUCHER_MAX_VALUE);
    }

    public boolean isValidValueOfFixedAmountVoucher(VoucherAmount voucherAmount) {
        return voucherAmount.isGreaterThan(FIXED_AMOUNT_VOUCHER_MIN_VALUE);
    }
}
