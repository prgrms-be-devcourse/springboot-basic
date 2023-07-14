package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.BadRequestException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

@Getter
public enum Command {
    EXIT(1, "프로그램 종료"),
    VOUCHER(2, "바우처 관리"),
    CUSTOMER(3, "고객 관리");

    private static final Map<Integer, Command> COMMANDS = Arrays.stream(Command.values())
            .collect(Collectors.toMap(Command::getNumber, Function.identity()));

    private final int number;
    private final String text;

    Command(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public static Command findByNumber(int number) {
        Command command = COMMANDS.get(number);

        if (command == null) {
            throw new BadRequestException(INVALID_COMMAND);
        }
        return command;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
