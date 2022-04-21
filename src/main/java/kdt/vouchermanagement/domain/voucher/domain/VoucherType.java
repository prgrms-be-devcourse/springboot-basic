package kdt.vouchermanagement.domain.voucher.domain;

public enum VoucherType {
    NONE(0),
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private int voucherTypeNum;

    VoucherType(int voucherTypeNum) {
        this.voucherTypeNum = voucherTypeNum;
    }
}
