package org.prgrms.kdtspringdemo.voucher.voucherdetail;

public enum VoucherType {
    FIXED("1", "FixedAmountVoucher"),
    PERCENT("2", "PercentDiscountVoucher"),
    ERROR("0", "error");

    private final String typeNumber;
    private final String typeName;

    VoucherType(String typeNumber, String typeName) {
        this.typeNumber = typeNumber;
        this.typeName = typeName;
    }

    public String getTypeNumber() {

        return typeNumber;
    }

    public String getTypeName() {

        return typeName;
    }
}
