package org.prgrms.kdt.enums;

public enum CommandType {

    UNDEFINED("undefined"),
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist"),
    REPLAY("replay");

    CommandType(String command) {
    }

    static public CommandType getCommandType(String command){
        try {
            return CommandType.valueOf(command);
        } catch (IllegalArgumentException ex){
            return UNDEFINED;
        }
    }
}