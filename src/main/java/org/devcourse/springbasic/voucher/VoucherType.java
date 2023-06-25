
package org.devcourse.springbasic.voucher;

import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT_VOUCHER(1, VoucherBean.FIXED_AMOUNT_VOUCHER_BEAN.getName()),
    PERCENT_DISCOUNT_VOUCHER(2, VoucherBean.PERCENT_DISCOUNT_VOUCHER_BEAN.getName());

    private final int inputNum;
    private final String beanName;

    VoucherType(int inputNum, String beanName) {
        this.inputNum = inputNum;
        this.beanName = beanName;
    }

    public static boolean hasMenu(int menuNum) {

        Arrays.stream(VoucherType.values())
                .filter(VoucherType -> (VoucherType.inputNum == menuNum))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다. 메뉴 번호를 숫자로 정확히 입력해주세요."));

        return true;
    }


    public static VoucherType findByMenuNum(int menuNum) {

        return Arrays.stream(VoucherType.values())
                .filter(VoucherType -> (VoucherType.inputNum == menuNum))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다. 메뉴 번호를 숫자로 정확히 입력해주세요."));

    }

    public String getBeanName() {
        return beanName;
    }
}
