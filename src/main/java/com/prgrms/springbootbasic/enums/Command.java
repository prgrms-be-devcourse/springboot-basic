package com.prgrms.springbootbasic.enums;

public enum Command {
    EXIT,
    CREATE,
    LIST;

    public static Command of(String inputCommand) {
        try {
            return Command.valueOf(inputCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어 입력입니다. 다시 입력해주세요. " + inputCommand);
        }
    }
}