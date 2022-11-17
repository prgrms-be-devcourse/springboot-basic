package org.prgrms.kdt.util;

import org.prgrms.kdt.dao.entity.voucher.VoucherType;

import java.util.regex.Pattern;

public class VoucherValidator {

    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d*(\\.?\\d)*$";

    private VoucherValidator() {
    }

    public static boolean isNumeric(String number) {

        if (number == null) {
            throw new IllegalArgumentException();
        }

        return Pattern.matches(NUMBER_REGEX, number);
    }

    private static void validateDiscountAmount(String discountValue) {
        if (isNumeric(discountValue)) {
            if (discountValue.contains(".")) {
                throw new IllegalArgumentException("할인 금액이 정수가 아닙니다.");
            }

            Long longDiscountValue = Long.parseLong(discountValue);

            if (longDiscountValue < 0) {
                throw new IllegalArgumentException("할인 금액이 음수입니다.");
            }
        } else {
            throw new IllegalArgumentException("할인 금액이 숫자가 아닙니다.");
        }
    }

    private static void validateDiscountPercent(String discountValue) {
        if (isNumeric(discountValue)) {
            Double doubleDiscountValue = Double.parseDouble(discountValue);

            if (doubleDiscountValue <= 0.0 || doubleDiscountValue > 100.0) {
                throw new IllegalArgumentException("할인율은 0보다 크고 100과 같거나 작아야 합니다.");
            }
        } else {
            throw new IllegalArgumentException("할인율이 숫자가 아닙니다.");
        }
    }


    public static void validateVoucherTypeAndDiscountValue(String voucherType, String discountValue) {
        VoucherType voucherClassType = VoucherType.of(voucherType);

        switch (voucherClassType) {
            case FIXED_AMOUNT:
                validateDiscountAmount(discountValue);
                break;
            case PERCENT_DISCOUNT:
                validateDiscountPercent(discountValue);
                break;
        }
    }
}
