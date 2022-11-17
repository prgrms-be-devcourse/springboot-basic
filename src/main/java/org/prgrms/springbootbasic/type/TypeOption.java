package org.prgrms.springbootbasic.type;

public enum TypeOption {
    FIXED("1", "fixedAmountVoucherFactory"), PERCENT("2", "percentAmountVoucherFactory");

    private final String number;
    private final String beanName;

    TypeOption(String number, String beanName) {
        this.number = number;
        this.beanName = beanName;
    }

    public String getNumber() {
        return number;
    }

    public String getBeanName() {
        return beanName;
    }


    public static boolean isFixed(String option) {
        return FIXED.getNumber().equals(option);
    }

    public static boolean isPercent(String option) {
        return PERCENT.getNumber().equals(option);
    }

}
