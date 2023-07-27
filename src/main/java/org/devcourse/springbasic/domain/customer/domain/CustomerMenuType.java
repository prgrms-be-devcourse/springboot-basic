package org.devcourse.springbasic.domain.customer.domain;

import java.util.Arrays;


public enum CustomerMenuType {

    EXIT("1"),
    SAVE("2");

    private final String inputNum;

    CustomerMenuType(String inputNum) {
        this.inputNum = inputNum;
    }

    private static boolean hasMenuByInputNum(String input) {
        return Arrays.stream(CustomerMenuType.values())
                .anyMatch(menuType -> menuType.inputNum.equals(input));
    }

    public static CustomerMenuType findCustomerMenuByInput(String input) {
        String upperCaseInput = input.toUpperCase();
        if (hasMenuByInputNum(upperCaseInput)) {
            return CustomerMenuType.valueOf(upperCaseInput);
        }
        throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
    }

    public boolean isSave() {
        return this == SAVE;
    }
}