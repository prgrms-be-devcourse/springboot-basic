package com.zerozae.voucher.common;

import com.zerozae.voucher.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

public enum MenuType {
    EXIT,
    CREATE,
    LIST,
    BLACKLIST,
    VOUCHER,
    CUSTOMER,
    BACK;

    private static final Logger logger = LoggerFactory.getLogger(MenuType.class);

    public static MenuType of(String menuType){
        try {
            return MenuType.valueOf(menuType.toUpperCase());
        }catch (RuntimeException e){
            logger.error("Error Message = {}", e.getMessage());
            throw ExceptionHandler.err(getMessage("NOT_EXIST_MENU.MSG"));
        }
    }
}
