package org.prgms.voucher;

public enum AmountType {
    FIX("Fixed Amount", 1),
    PERCENT("Percent Amount", 2);

    private final String amountType;
    private final int num;

    AmountType(String amountType, int num) {
        this.amountType = amountType;
        this.num = num;
    }
}
