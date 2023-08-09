package org.prgrms.kdtspringdemo.voucher.constant;

public enum VoucherColumn {
    VOUCHER_ID("voucher_id"),
    VOUCHER_TYPE("voucher_type"),
    AMOUNT("amount"),
    ALL("*")
    ;

    private final String column;

    VoucherColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
