package prgms.springbasic.voucher;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER(1),
    PERCENT_DISCOUNT_VOUCHER(2);

    private final int typeNumber;

    VoucherType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public int getTypeNumber() {
        return typeNumber;
    }
}
