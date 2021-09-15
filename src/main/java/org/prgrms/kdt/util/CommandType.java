package org.prgrms.kdt.util;

import org.prgrms.kdt.Exception.InputFormatException;

public enum CommandType {
    EXIT("프로그램 종료"),
    CREATE("생성"),
    LIST("목록 조회");

    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType matchCommandType(String input) throws InputFormatException{
        for (CommandType commandType : CommandType.values()) {
            if (commandType.toString().equalsIgnoreCase(input)) return commandType;
        }
        throw new InputFormatException();
    }

}
