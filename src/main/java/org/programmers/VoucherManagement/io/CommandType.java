package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_EXIST_COMMAND;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private static final Map<String, CommandType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Arrays
                    .stream(values())
                    .collect(Collectors.toMap(CommandType::getType, Function.identity())));

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    private String getType() {
        return type;
    }

    public boolean isExit() {

        return this.equals(CommandType.EXIT);
    }

    public boolean isCreate() {
        return this.equals(CommandType.CREATE);
    }

    public boolean isList() {
        return this.equals(CommandType.LIST);
    }

    public boolean isBlackList() {
        return this.equals(CommandType.BLACKLIST);
    }

    public static CommandType from(String type) {
        try {
            if (COMMAND_TYPE_MAP.containsKey(type)) {
                return COMMAND_TYPE_MAP.get(type);
            }
            throw new VoucherException(NOT_EXIST_COMMAND);
        } catch (VoucherException e) {
            throw new VoucherException(NOT_EXIST_COMMAND, e);
        }
    }
}
