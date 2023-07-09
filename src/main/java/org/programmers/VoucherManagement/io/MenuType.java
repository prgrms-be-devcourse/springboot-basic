package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_EXIST_COMMAND;

public enum MenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private static final Map<String, MenuType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Arrays
                    .stream(values())
                    .collect(Collectors.toMap(MenuType::getType, Function.identity())));

    private final String type;

    MenuType(String type) {
        this.type = type;
    }

    private String getType() {
        return type;
    }


    public static MenuType from(String type) {
        if (!COMMAND_TYPE_MAP.containsKey(type)) {
            throw new VoucherException(NOT_EXIST_COMMAND);
        }
        return COMMAND_TYPE_MAP.get(type);
    }
}
