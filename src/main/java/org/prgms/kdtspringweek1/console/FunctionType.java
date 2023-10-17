package org.prgms.kdtspringweek1.console;

import org.prgms.kdtspringweek1.exception.ExceptionCode;
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
            if (functionType.name.equals(name)) {
                return functionType;
            }
        }
        logger.debug("Invalid Function Type -> {}", name);
        throw new IllegalArgumentException(ExceptionCode.INVALID_VOUCHER_FUNCTION_TYPE.getMessage());
    }
}
