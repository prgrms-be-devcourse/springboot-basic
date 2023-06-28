package com.dev.bootbasic.view;

public enum Command {

    CREATE,
    LIST,
    EXIT
    ;

    private static final String INVALID_COMMAND_MESSAGE = "%s 는(은) 실행할 수 없는 명령입니다.";

    public static Command from(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(INVALID_COMMAND_MESSAGE, name));
        }
    }

}
