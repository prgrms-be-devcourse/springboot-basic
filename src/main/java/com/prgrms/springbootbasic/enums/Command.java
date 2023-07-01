package com.prgrms.springbootbasic.enums;

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");
    private final String inputCommand;

    Command(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    public static Command of(String inputCommand) {
        try {
            return Command.valueOf(inputCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어 입력입니다. 다시 입력해주세요. " + inputCommand);
        }
    }
}