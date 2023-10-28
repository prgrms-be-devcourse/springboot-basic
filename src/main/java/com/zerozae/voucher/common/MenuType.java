package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ExceptionMessage;


public enum MenuType {

    EXIT,
    CREATE,
    LIST,
    BLACKLIST,
    VOUCHER,
    CUSTOMER,
    WALLET,
    SEARCH,
    DELETE,
    DELETE_ALL,
    UPDATE,
    ASSIGN,
    REMOVE,
    VOUCHER_LIST,
    OWNER,
    BACK;

    public static MenuType of(String input){
        try {
            return MenuType.valueOf(input.toUpperCase());
        }catch (RuntimeException e) {
            throw ExceptionMessage.error("존재하지 않는 메뉴입니다. 다시 입력해주세요.");
        }
    }
}
