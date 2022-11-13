package prgms.vouchermanagementapp.io;

public enum CommandType {

    EXIT, CREATE, LIST, BLACKLIST;

    public static CommandType of(String command) throws IllegalArgumentException {
        return CommandType.valueOf(command.toUpperCase());
    }

    public boolean is(CommandType commandType) {
        return this == commandType;
    }
}
