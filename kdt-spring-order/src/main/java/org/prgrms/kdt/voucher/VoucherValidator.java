package org.prgrms.kdt.voucher;

import java.util.regex.Pattern;

public class VoucherValidator {

    private VoucherValidator() {}

    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d*(\\.?\\d)*$";

    public static boolean isNumeric(String number) {

        if (number == null) {
            throw new IllegalArgumentException();
        }

        return Pattern.matches(NUMBER_REGEX, number);
    }

    private static void validateDiscountAmount(String discountValue) {
        if (isNumeric(discountValue)) {
            Long longDiscountValue = Long.parseLong(discountValue);

            if (discountValue.contains(".") || longDiscountValue < 0) {
                throw new IllegalArgumentException("할인 금액이 정수가 아니거나 음수입니다.");
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


    public static void validateVoucherStatusAndDiscountValue(String voucherType, String discountValue) {
        VoucherStatus voucherStatus = VoucherStatus.of(voucherType);

        switch (voucherStatus) {
            case FIXED_AMOUNT:
                validateDiscountAmount(discountValue);
                break;
            case PERCENT_DISCOUNT:
                validateDiscountPercent(discountValue);
                break;
        }
    }
}
