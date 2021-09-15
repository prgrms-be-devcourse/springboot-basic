package org.prgrms.kdt;

import org.prgrms.kdt.Exception.InputFormatException;

public enum TesterCommandType {
    EXIT("프로그램 종료"),
    CREATE("생성"),
    LIST("목록 조회");

    private String type;

    TesterCommandType(String type) {
        this.type = type;
    }

    public static TesterCommandType matchCommandType(String input) throws InputFormatException{
        for (TesterCommandType commandType : TesterCommandType.values()) {
            if (commandType.toString().equalsIgnoreCase(input)) return commandType;
        }
        throw new InputFormatException();
    }

}
