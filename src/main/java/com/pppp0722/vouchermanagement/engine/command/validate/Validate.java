package com.pppp0722.vouchermanagement.engine.command.validate;

import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.PERCENT_DISCOUNT;

import com.pppp0722.vouchermanagement.voucher.model.VoucherType;

public class Validate {

    public static boolean isValidAmount(VoucherType type, long amount) {
        if (type.equals(FIXED_AMOUNT)) {
            if (amount < 1) {
                return false;
            }
        }

        if (type.equals(PERCENT_DISCOUNT)) {
            if (amount < 1 || amount > 100) {
                return false;
            }
        }

        return true;
    }
}
