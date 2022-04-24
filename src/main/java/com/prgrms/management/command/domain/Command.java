package com.prgrms.management.command.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Command {
    CREATE_VOUCHER(1),
    LIST_VOUCHER(2),
    BLACKLIST(3),
    CREATE_CUSTOMER(4),
    UPDATE_CUSTOMER(5),
    DELETE_CUSTOMER(6),
    DELETE_ALL_CUSTOMER(7),
    FIND_CUSTOMER_BY_ID(8),
    FIND_CUSTOMER_BY_EMAIL(9),
    LIST_CUSTOMER(10),
    ASSIGN_VOUCHER(11),
    DELETE_VOUCHER(12),
    LIST_VOUCHER_WITH_TYPE(13),
    EXIT(0);
    private final int input;

    Command(int input) {
        this.input = input;
    }

    public static Command of(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.input == toInt(input))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(Command.class + ErrorMessageType.NOT_EXIST_COMMAND_TYPE.getMessage()));
    }

    private static int toInt(String input) {
        int amount = 0;
        try {
            amount = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ErrorMessageType.INCORRECT_NUMBER_FORMAT.getMessage());
        }
        return amount;
    }
}
