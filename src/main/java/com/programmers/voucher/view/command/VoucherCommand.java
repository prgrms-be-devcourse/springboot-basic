package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.BadRequestException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

@Getter
public enum VoucherCommand {
    CREATE(1, "바우처 생성"),
    READ_ALL(2, "모든 바우처 조회"),
    READ(3, "바우처 조회"),
    UPDATE(4, "바우처 수정"),
    DELETE(5, "바우처 삭제");

    private static final Map<Integer, VoucherCommand> VOUCHER_COMMANDS = Arrays.stream(VoucherCommand.values())
            .collect(Collectors.toMap(VoucherCommand::getNumber, Function.identity()));

    private final int number;
    private final String text;

    VoucherCommand(int number, String text) {
        this.number = number;
        this.text = text;
    }

    public static VoucherCommand findByNumber(int number) {
        VoucherCommand command = VOUCHER_COMMANDS.get(number);

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
