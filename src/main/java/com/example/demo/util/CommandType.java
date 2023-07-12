package com.example.demo.util;

public enum CommandType {
    EXIT,
    CREATE_VOUCHER,
    PRINT_VOUCHER_LIST,
    CREATE_CUSTOMER,
    PRINT_CUSTOMER_LIST,
    UPDATE_CUSTOMER,
    DELETE_CUSTOMER;

    public static CommandType from(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 " + input + "는 유효한 커멘드가 아닙니다. \n exit, create, list 중 하나를 입력하세요.\n");
        }
    }
}
