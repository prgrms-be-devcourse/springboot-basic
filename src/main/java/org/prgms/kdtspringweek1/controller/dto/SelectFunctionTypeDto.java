package org.prgms.kdtspringweek1.controller.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SelectFunctionTypeDto {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blackList");

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
