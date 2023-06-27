package org.prgms.voucher;

public enum AmountType {
    FIX("fix", 1),
    PERCENT("percent", 2);

    private final String amountType;
    private final int choiceNumber;

    AmountType(String amountType, int choiceNumber) {
        this.amountType = amountType;
        this.choiceNumber = choiceNumber;
    }
}
