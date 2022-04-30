package com.pppp0722.vouchermanagement.engine.command.validate;

import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.PERCENT_DISCOUNT;

import com.pppp0722.vouchermanagement.voucher.model.VoucherType;

public class Validate {

    // name은 20자 이내로
    public static boolean isValidName(String name) {
        if (name.length() > 20) {
            return false;
        }

        return true;
    }

    // 규격외 amount 검증
    public static boolean isValidAmount(VoucherType type, long amount) {
        if (type.equals(FIXED_AMOUNT)) {
            if (amount < 1 || amount > 100000000) {
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
