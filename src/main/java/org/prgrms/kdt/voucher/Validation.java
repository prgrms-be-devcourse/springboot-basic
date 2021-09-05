package org.prgrms.kdt.voucher;

import java.util.Scanner;

public class Validation {
    static Scanner scanner = new Scanner(System.in);

    public static long fixedAmountDiscountValidation() {
        boolean discountAmountCheck = true;
        long amount = 0;

        while (discountAmountCheck) {
            try {
                amount = Long.parseLong(scanner.nextLine());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력하세요");
                continue;
            }
            discountAmountCheck = amountValidation(amount);
        }
        return amount;
    }

    public static float percentDiscountValidation() {
        boolean discountAmountCheck = true;
        float percent = 0;

        while (discountAmountCheck) {
            try {
                percent = Float.parseFloat(scanner.nextLine());
            } catch (final NumberFormatException e) {
                System.out.println("숫자를 입력하세요");
                continue;
            }
            discountAmountCheck = percentValidation(percent);
        }
        return percent;
    }

    public static boolean amountValidation(final long amount) {
        if (amount <= 1000000 && amount > 0)
            return false;
        else {
            System.out.println("할인 가격은 0원 초과, 1,000,000원 이하로 설정해주십시오.");
            return true;
        }
    }

    public static boolean percentValidation(final float percent) {
        if (percent <= 100 && percent > 0)
            return false;
        else {
            System.out.println("할인율은 0% 초과, 100% 이하로 설정해주십시오.");
            return true;
        }
    }
}
