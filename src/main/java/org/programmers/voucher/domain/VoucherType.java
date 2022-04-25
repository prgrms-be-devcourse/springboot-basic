package org.programmers.voucher.domain;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("Fixed"),
    PERCENT_DISCOUNT_VOUCHER("Percent");

    private final String alias;

    VoucherType(String alias){
        this.alias = alias;
    }

    //todo
    public boolean isMatches(String input) {
        return false;
    }
}
