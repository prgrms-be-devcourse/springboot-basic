package com.programmers.voucher.view.command;

import com.programmers.voucher.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.programmers.voucher.constant.ErrorCode.INVALID_COMMAND;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CustomerCommand {
    CREATE(1, "고객 생성"),
    READ_ALL(2, "모든 고객 조회"),
    READ(3, "고객 조회"),
    UPDATE(4, "고객 수정"),
    DELETE(5, "고객 삭제");

    private static final Map<Integer, CustomerCommand> CUSTOMER_COMMANDS = Arrays.stream(CustomerCommand.values())
            .collect(Collectors.toMap(CustomerCommand::getNumber, Function.identity()));

    private final int number;
    private final String text;

    public static CustomerCommand findByNumber(int number) {
        CustomerCommand command = CUSTOMER_COMMANDS.get(number);

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
