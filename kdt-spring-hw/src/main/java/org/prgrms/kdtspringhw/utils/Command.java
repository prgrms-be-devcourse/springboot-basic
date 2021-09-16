package org.prgrms.kdtspringhw.utils;

import java.util.Arrays;
//https://pjh3749.tistory.com/279
//https://limkydev.tistory.com/66

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("black");
    private final String command;

    Command(String command){
        this.command = command;
    }

    public static Command getCommandType(String command){
        return Arrays.stream(values())//values는 배열을 보낸다.
                .filter(commandType -> commandType.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 명령어 <" + command+">를 입력하셨습니다.\n유효한 "));
    }
}
