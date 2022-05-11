package org.prgrms.kdt;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public enum CommandType {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    UPDATE("update", "Type update to update a voucher."),
    DELETE("delete", "Type delete to delete a voucher."),
    LIST("list", "Type list to list all vouchers."),
    BLACK_LIST("black-list", "Type black-list to list all customer in black-list."),
    INVALID("invalid", "");

    private final String commandType;
    private final String commandManual;

    CommandType(String commandType, String commandManual) {
        this.commandType = commandType;
        this.commandManual = commandManual;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getCommandManual() {
        return commandManual;
    }

    public boolean isRunnable() {
        return this != EXIT;
    }

    public static CommandType getCommandType(String commandType) {
        return Stream.of(CommandType.values())
            .filter(type -> type.getCommandType().equalsIgnoreCase(commandType))
            .findFirst()
            .orElse(CommandType.INVALID);
    }

    public String getCommandManuals() {
        return Stream.of(CommandType.values())
            .map(CommandType::getCommandManual)
            .filter(StringUtils::isNotEmpty)
            .collect(Collectors.joining("\n"));
    }
}
