package org.prgms.kdtspringweek1.controller.consoleController.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SelectFunctionTypeDto {
    EXIT("exit", "exit"),
    CREATE_VOUCHER("voucher", "create voucher"),
    LIST_VOUCHERS("voucher", "list vouchers"),
    SEARCH_VOUCHER("voucher", "search voucher"),
    UPDATE_VOUCHER("voucher", "update voucher"),
    DELETE_ALL_VOUCHERS("voucher", "delete all vouchers"),
    DELETE_VOUCHER("voucher", "delete voucher"),
    CREATE_CUSTOMER("customer", "create customer"),
    LIST_CUSTOMERS("customer", "list customers"),
    SEARCH_CUSTOMER("customer", "search customer"),
    UPDATE_CUSTOMER("customer", "update customer"),
    DELETE_ALL_CUSTOMERS("customer", "delete all customers"),
    DELETE_CUSTOMER("customer", "delete customer"),
    LIST_BLACK_CUSTOMERS("customer", "list black customers"),
    CREATE_MY_WALLET("wallet", "create my wallet"),
    SEARCH_MY_VOUCHERS("wallet", "search my vouchers"),
    DELETE_MY_WALLET("wallet", "delete my wallet"),
    SEARCH_MY_CUSTOMERS("wallet", "search my customers");

    private String type;
    private String name;
    private final static Logger logger = LoggerFactory.getLogger(SelectFunctionTypeDto.class);

    SelectFunctionTypeDto(String type, String name) {
        this.type = type;
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

    public String getType() {
        return type;
    }
}
