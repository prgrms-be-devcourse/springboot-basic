package org.prgms.kdtspringweek1.console;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FunctionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blackList");

    private String name;
    private final static Logger logger = LoggerFactory.getLogger(FunctionType.class);

    FunctionType(String name) {
        this.name = name;
    }

    public static FunctionType getValueByName(String name) {
        for (FunctionType functionType : FunctionType.values()) {
            if (functionType.name.equalsIgnoreCase(name)) { // 사용자가 선택한 기능이라면 대소문자와 상관없이 해당 기능을 제공
                return functionType;
            }
        }
        logger.debug("Invalid Function Type -> {}", name);
        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_FUNCTION_TYPE.getMessage());
    }
}
