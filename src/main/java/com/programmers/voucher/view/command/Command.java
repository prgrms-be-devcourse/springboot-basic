package com.programmers.voucher.view.command;

import com.programmers.voucher.constant.ErrorMessage;
import com.programmers.voucher.exception.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Command {
    EXIT(1, "프로그램 종료"),
    VOUCHER(2, "바우처 관리"),
    CUSTOMER(3, "고객 관리");

    private static final Logger logger = LoggerFactory.getLogger(Command.class);
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
                .orElseThrow(() -> {
                    logger.error("{} => {}", ErrorMessage.INVALID_COMMAND, number);
                    return new InvalidCommandException(ErrorMessage.INVALID_COMMAND);
                });
    }

    private boolean isEqualTo(int number) {
        return this.number == number;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
