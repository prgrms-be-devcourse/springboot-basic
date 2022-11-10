package org.prgrms.springbootbasic.type;

import java.util.Arrays;
import java.util.Optional;

public enum TypeOption {
    FIXED("1", "amount", "fixedAmountVoucherFactory"), PERCENT("2", "percent","percentAmountVoucherFactory");

    private final String number;
    private final String argument;
    private final String beanName;

    TypeOption(String number, String argument, String beanName) {
        this.number = number;
        this.argument = argument;
        this.beanName = beanName;
    }

    private String getNumber() {
        return number;
    }

    public String getArgument() {
        return argument;
    }

    public String getBeanName() {
        return beanName;
    }

    public static TypeOption stringToTypeOption(String option) {
        return Arrays.stream(TypeOption.values())
                .filter(x -> x.getNumber().equals(option))
                .findFirst().get();
    }

    public static boolean isFixed(String option) {
        return FIXED.getNumber().equals(option);
    }

    public static boolean isPercent(String option) {
        return PERCENT.getNumber().equals(option);
    }

}
