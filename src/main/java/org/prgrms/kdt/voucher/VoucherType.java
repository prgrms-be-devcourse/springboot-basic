package org.prgrms.kdt.voucher;

public enum VoucherType {
    FixedAmountVoucher(1),
    PercentDiscountVoucher(2);

    private final int voucherType;
    VoucherType(int voucherType) {
        this.voucherType = voucherType;
    }

    public int typeNum() {
        return voucherType;
    }
}
