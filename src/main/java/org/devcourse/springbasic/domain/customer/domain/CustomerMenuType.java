package org.devcourse.springbasic.domain.customer.domain;

import org.devcourse.springbasic.global.util.DigitChecker;

import java.util.Arrays;


public enum CustomerMenuType {

    EXIT(1),
    SAVE(2);

    private final int inputNum;

    CustomerMenuType(int inputNum) {
        this.inputNum = inputNum;
    }

    public static CustomerMenuType findCustomerMenuByInput(String input) {

        if (DigitChecker.isDigit(input)) {
            return Arrays.stream(CustomerMenuType.values())
                    .filter(customerMenuType -> (customerMenuType.inputNum == Integer.parseInt(input)))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
        } else {
            return CustomerMenuType.valueOf(input.toUpperCase());
        }
    }


    public boolean isExit() {
        return this == EXIT;
    }
    public boolean isSave() {
        return this == SAVE;
    }
}