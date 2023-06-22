package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.exception.NoValidCommandException;

import java.util.Arrays;

public enum MenuCommand {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String command;

    MenuCommand(String command) {
        this.command = command;
    }

    public static MenuCommand resolve(String input) {
        return Arrays.stream(values())
                .filter(val -> val.getCommand().equals(input))
                .findFirst()
                .orElseThrow(() -> new NoValidCommandException("올바르지 않은 메뉴 선택입니다."));
    }

    public String getCommand() {
        return command;
    }
}
