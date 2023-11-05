package com.prgms.vouchermanager.repository.wallet;

public enum WalletQueryType {
    INSERT("insert into wallets (id, customer_id, voucher_id) " +
            "values (:id, :customer_id, :voucher_id)"),

    SELECT_BY_CUSTOMER_ID("select * from wallets where customer_id = :customer_id "),

    SELECT_BY_VOUCHER_ID("select * from wallets where voucher_id = :voucher_id "),

    DELETE_BY_CUSTOMER_ID("delete from wallets where customer_id = :customer_id"),
    SELECT_ALL("select * from wallets");

    private final String query;

    WalletQueryType(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
