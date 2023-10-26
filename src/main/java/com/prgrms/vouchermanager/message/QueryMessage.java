package com.prgrms.vouchermanager.message;

import lombok.Getter;

@Getter
public enum QueryMessage {

    INSERT_CUSTOMER("insert into customers(customer_id, name, year_of_birth, is_blacklist) values(UUID_TO_BIN(?), ?, ?, ?)"),
    FIND_BY_ID_CUSTOMER("select * from customers where customer_id = UUID_TO_BIN(?)"),
    LIST_CUSTOMER("select * from customers"),
    UPDATE_YEAR_OF_BIRTH_CUSTOMER("update customers set year_of_birth=? where customer_id=UUID_TO_BIN(?)"),
    UPDATE_NAME_CUSTOMER("update customers set name=? where customer_id=UUID_TO_BIN(?)"),
    DELETE_CUSTOMER("delete from customers where customer_id = UUID_TO_BIN(?)"),

    INSERT_VOUCHER("insert into vouchers(voucher_id, voucher_type, discount) values(UUID_TO_BIN(?), ?, ?)"),
    LIST_VOUCHER("select * from vouchers"),
    FIND_BY_ID_VOUCHER("select * from vouchers where voucher_id = UUID_TO_BIN(?)"),
    UPDATE_DISCOUNT_VOUCHER("update vouchers set discount=? where voucher_id=UUID_TO_BIN(?)"),
    DELETE_VOUCHER("delete from vouchers where voucher_id = UUID_TO_BIN(?)"),

    INSERT_WALLET("insert into wallets(wallet_id, voucher_id, customer_id) values(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))"),
    FIND_BY_CUSTOMER_ID_WALLET("select * from wallets where customer_id = UUID_TO_BIN(?)"),
    FIND_BY_VOUCHER_ID_WALLET("select * from wallets where voucher_id = UUID_TO_BIN(?)"),
    DELETE_WALLET("delete from wallets where customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)");

    private final String message;

    QueryMessage(String message) {
        this.message = message;
    }
}
