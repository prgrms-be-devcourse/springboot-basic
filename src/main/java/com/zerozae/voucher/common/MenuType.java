package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum MenuType {

    EXIT,
    CREATE,
    LIST,
    BLACKLIST,
    VOUCHER,
    CUSTOMER,
    BACK;

    private static final Logger logger = LoggerFactory.getLogger(MenuType.class);

    public static MenuType of(String input){
        try {
            return MenuType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            logger.warn("Error Message = {}", e.getMessage());
            throw ErrorMessage.error("존재하지 않는 메뉴입니다. 다시 입력해주세요.");
        }
    }
}
