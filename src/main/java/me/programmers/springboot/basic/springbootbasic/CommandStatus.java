package me.programmers.springboot.basic.springbootbasic;

public enum CommandStatus {
    EXIT,
    CREATE,
    LIST,
    WRONG_COMMAND;

    public static CommandStatus getCommand(String s) {
        switch (s) {
            case "exit":
                return EXIT;
            case "create":
                return CREATE;
            case "list":
                return LIST;
            default:
                return WRONG_COMMAND;
        }
    }
}
