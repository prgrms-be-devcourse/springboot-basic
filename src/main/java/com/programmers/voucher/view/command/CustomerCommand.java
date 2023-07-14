package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.BadRequestException;

import java.util.Arrays;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

public enum CustomerCommand {
    CREATE(1, "고객 생성"),
    READ_ALL(2, "모든 고객 조회"),
    READ(3, "고객 조회"),
    UPDATE(4, "고객 수정"),
    DELETE(5, "고객 삭제");

    private final int number;
    private final String text;

    CustomerCommand(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public static CustomerCommand findByNumber(int number) {
        return Arrays.stream(CustomerCommand.values())
                .filter(command -> command.number == number)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(INVALID_COMMAND));
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
