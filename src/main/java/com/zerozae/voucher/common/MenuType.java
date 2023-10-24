package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ErrorMessage;


public enum MenuType {

    EXIT,
    CREATE,
    LIST,
    BLACKLIST,
    VOUCHER,
    CUSTOMER,
    REGISTER,
    REMOVE,
    VOUCHER_LIST,
    OWNER,
    SEARCH,
    DELETE,
    DELETE_ALL,
    UPDATE,
    BACK;

    public static MenuType of(String input){
        try {
            return MenuType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            throw ErrorMessage.error("존재하지 않는 메뉴입니다. 다시 입력해주세요.");
        }
    }
}
