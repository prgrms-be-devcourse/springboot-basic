package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum Command {
    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4);

    private final int number;

    Command(int number) {
        this.number = number;
    }

    public static Command from(int number) {
        return Arrays.stream(values())
                .filter(menu -> menu.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어 번호입니다. : " + number));
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isRead() {
        return this == READ;
    }

    public boolean isUpdate() {
        return this == UPDATE;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

    public int getNumber() {
        return number;
    }
}
