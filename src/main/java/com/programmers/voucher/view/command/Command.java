package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.InvalidCommandException;

import java.util.Arrays;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

public enum Command {
    EXIT(1, "프로그램 종료"),
    VOUCHER(2, "바우처 관리"),
    CUSTOMER(3, "고객 관리");

    private final int number;
    private final String text;

    Command(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public static Command findByNumber(int number) {
        return Arrays.stream(Command.values())
                .filter(command -> command.isEqualTo(number))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(INVALID_COMMAND));
    }

    private boolean isEqualTo(int number) {
        return this.number == number;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
