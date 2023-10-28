package org.prgms.kdtspringweek1.controller.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SelectFunctionTypeDto {
    EXIT("exit"),
    CREATE_VOUCHER("create voucher"),
    LIST_VOUCHERS("list vouchers"),
    SEARCH_VOUCHER("search voucher"),
    UPDATE_VOUCHER("update voucher"),
    DELETE_ALL_VOUCHERS("delete all vouchers"),
    DELETE_VOUCHER("delete voucher"),
    CREATE_CUSTOMER("create customer"),
    LIST_CUSTOMERS("list customers"),
    SEARCH_CUSTOMER("search customer"),
    UPDATE_CUSTOMER("update customer"),
    DELETE_ALL_CUSTOMERS("delete all customers"),
    DELETE_CUSTOMER("delete customer"),
    LIST_BLACK_CUSTOMERS("list black customers"),
    CREATE_MY_WALLET("create my wallet"),
    SEARCH_MY_VOUCHERS("search my vouchers"),
    DELETE_MY_WALLET("delete my wallet"),
    SEARCH_MY_CUSTOMERS("search my customers");

    private String name;
    private final static Logger logger = LoggerFactory.getLogger(SelectFunctionTypeDto.class);

    SelectFunctionTypeDto(String name) {
        this.name = name;
    }

    public static SelectFunctionTypeDto getValueByName(String name) {
        for (SelectFunctionTypeDto selectFunctionTypeDto : SelectFunctionTypeDto.values()) {
            if (selectFunctionTypeDto.name.equalsIgnoreCase(name)) { // 사용자가 선택한 기능이라면 대소문자와 상관없이 해당 기능을 제공
                return selectFunctionTypeDto;
            }
        }
        logger.debug("Invalid Function Type -> {}", name);
        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_FUNCTION_TYPE.getMessage());
    }

    public String getName() {
        return name;
    }
}
