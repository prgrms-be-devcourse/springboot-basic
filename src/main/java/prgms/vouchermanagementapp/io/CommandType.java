package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.io.message.ExceptionMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CommandType {

    // TODO: UpperCase를 사용하여 리팩토링
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");

    private final String command;
    private final String message;

    CommandType(String command, String message) {
        this.command = command;
        this.message = message;
    }

    public static List<String> getMessages() {
        return Arrays.stream(CommandType.values())
                .map(commandType -> commandType.message)
                .collect(Collectors.toList());
    }

    public static CommandType of(String menu) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.command.equals(menu))
                .findFirst()
                .orElseThrow(() -> new NoSuchFieldError(ExceptionMessage.NO_MENU_EXISTS.getMessage()));
    }

    public boolean is(CommandType commandType) {
        return this == commandType;
    }
}
