package org.prgrms.kdt.util;

public class ConstantMessageUtil {
    private ConstantMessageUtil() {
    }

    public static final String VOUCHER_MAIN_MENU = "=== Voucher Program ===" + System.lineSeparator() +
            "Type 'create' to create a new voucher." + System.lineSeparator() +
            "Type 'list' to list all voucher." + System.lineSeparator() +
            "Type 'exit' to exit the program.";

    public static final String TYPE_VOUCHER_INFO = "1. FixedAmountVoucher" + System.lineSeparator() +
            "2. PercentDiscountVoucher" + System.lineSeparator() +
            "생성할 Voucher 타입의 숫자를 입력하세요: ";

    public static final String DISCOUNT_VALUE = "선택한 Voucher 형식에 맞는 discountDegree(정수): ";
    public static final String TERMINATE = "프로그램을 종료합니다.";
}
