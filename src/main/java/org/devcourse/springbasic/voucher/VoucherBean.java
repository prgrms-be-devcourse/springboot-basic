package org.devcourse.springbasic.voucher;

public enum VoucherBean {

    FIXED_AMOUNT_VOUCHER_BEAN(
            FixedAmountVoucher.class.getSimpleName().substring(0, 1).toLowerCase()
            + FixedAmountVoucher.class.getSimpleName().substring(1)),

    PERCENT_DISCOUNT_VOUCHER_BEAN(
            PercentDiscountVoucher.class.getSimpleName().substring(0, 1).toLowerCase()
            + PercentDiscountVoucher.class.getSimpleName().substring(1));


    private final String name;

    VoucherBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
