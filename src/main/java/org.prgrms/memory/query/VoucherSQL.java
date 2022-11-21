package org.prgrms.memory.query;

public enum VoucherSQL {
    INSERT("INSERT INTO voucher(id, type, amount) VALUES (:id,:voucherType,:amountValue)"),
    FIND_ALL("SELECT * FROM voucher"),
    FIND_BY_ID("SELECT * FROM voucher WHERE id = :id"),
    DELETE_ALL("DELETE FROM voucher"),
    DELETE_BY_ID("DELETE FROM voucher WHERE id = :id"),
    UPDATE("UPDATE voucher SET type = :voucherType , amount = :amountValue WHERE id = :id ");

    private final String value;

    VoucherSQL(String sql) {
        this.value = sql;
    }

    public String getSql() {
        return value;
    }
}
