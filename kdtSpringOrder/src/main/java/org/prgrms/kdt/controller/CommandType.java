package org.prgrms.kdt.controller;

//이 클래스의 위치는 어디가 좋을까..
public enum CommandType {

    UNDEFINED("undefined"),
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
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