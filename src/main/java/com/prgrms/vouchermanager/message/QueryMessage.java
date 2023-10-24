package com.prgrms.vouchermanager.message;

import lombok.Getter;

@Getter
public enum QueryMessage {

    INSERT_QUERY_CUSTOMER("insert into customers(customer_id, name, year_of_birth, is_blacklist) values(UUID_TO_BIN(?), ?, ?, ?)"),
    FIND_BY_ID_QUERY_VOUCHER("select * from customers where customer_id = UUID_TO_BIN(?)"),
    LIST_QUERY_CUSTOMER("select * from customers"),
    UPDATE_YEAR_OF_BIRTH_QUERY("update customers set year_of_birth=? where customer_id=UUID_TO_BIN(?)"),
    UPDATE_NAME_QUERY("update customers set name=? where customer_id=UUID_TO_BIN(?)"),
    DELETE_QUERY_CUSTOMER("delete from customers where customer_id = UUID_TO_BIN(?)"),

    INSERT_QUERY_VOUCHER("insert into vouchers(voucher_id, voucher_type, discount) values(UUID_TO_BIN(?), ?, ?)"),
    LIST_QUERY_VOUCHER("select * from vouchers"),
    UPDATE_DISCOUNT_QUERY_VOUCHER("update vouchers set discount=? where voucher_id=UUID_TO_BIN(?)"),
    DELETE_QUERY_VOUCHER("delete from vouchers where voucher_id = UUID_TO_BIN(?)");


    private final String message;

    QueryMessage(String message) {
        this.message = message;
    }
}
