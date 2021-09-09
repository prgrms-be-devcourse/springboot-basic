package org.prgrms.kdt.command;

public class ValueValidation {

    public static long inputValueValidation(final String voucherType) {
        String inputStringType;
        boolean discountAmountCheck = true;
        do {
            inputStringType = Input.input();

            // 숫자가 입력되었는지 검사
            // 값의 범위가 유효한지 검사
            discountAmountCheck = numberValidation(voucherType, inputStringType);
        } while (discountAmountCheck);

        return Long.parseLong(inputStringType);
    }

    public static boolean numberValidation(final String voucherType, final String value) {
        long valueCheck = 0;
        try {
            valueCheck = Long.parseLong(value);
        } catch (final NumberFormatException e) {
            System.out.println("숫자를 입력하세요");
            return true;
        } catch (final Exception e) {
            return true;
        } finally {
            return rangeValidation(voucherType, valueCheck);
        }
    }

    public static boolean rangeValidation(final String voucherType, final long value) {
        switch (voucherType) {
            case "FixedAmountVoucher":
                if (value <= 1000000 && value > 0)
                    return false;
                else {
                    System.out.println("할인 가격은 0원 초과, 1,000,000원 이하로 설정해주십시오.");
                    return true;
                }
            case "PercentDiscountVoucher":
                if (value <= 100 && value > 0)
                    return false;
                else {
                    System.out.println("할인율은 0% 초과, 100% 이하로 설정해주십시오.");
                    return true;
                }
            default:
                System.out.println("잘못된 voucher type 입니다.");
                return true;
        }
    }
}
