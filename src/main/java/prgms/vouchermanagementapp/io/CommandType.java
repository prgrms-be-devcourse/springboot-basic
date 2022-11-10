package prgms.vouchermanagementapp.io;

import java.util.List;

public enum CommandType {

    // TODO: 출력 역할 입장에서 생각해봐야한다.
    EXIT("Type exit to exit the program."),
    CREATE("Type create to create a new voucher."),
    LIST("Type list to list all vouchers.");

    private final String message;

    CommandType(String message) {
        this.message = message;
    }

    public static List<CommandType> getCommandTypes() {
        return List.of(CommandType.values());
    }

    public static CommandType of(String command) throws IllegalArgumentException {
        return CommandType.valueOf(command.toUpperCase());
    }

    public boolean is(CommandType commandType) {
        return this == commandType;
    }

    public String getMessage() {
        return this.message;
    }
}
