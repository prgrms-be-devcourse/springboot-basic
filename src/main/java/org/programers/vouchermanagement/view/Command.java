package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum Command {
    BLACKLIST(1), CREATE_VOUCHER(2), LIST_VOUCHER(3), EXIT(4);

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

    public boolean isBlacklist() {
        return this.equals(Command.BLACKLIST);
    }

    public boolean isCreateVoucher() {
        return this.equals(Command.CREATE_VOUCHER);
    }

    public boolean isListVoucher() {
        return this.equals(Command.LIST_VOUCHER);
    }

    public boolean isExit() {
        return this.equals(Command.EXIT);
    }

    public int getNumber() {
        return number;
    }
}
