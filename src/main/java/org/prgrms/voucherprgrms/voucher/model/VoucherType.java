package org.prgrms.voucherprgrms.voucher.model;

public enum VoucherType {
    FIXEDAMOUNT("FixedAmountVoucher"),
    PERCENTDISCOUNT("PercentDiscountVoucher");

    private final String name;

    VoucherType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
