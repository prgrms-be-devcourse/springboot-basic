package com.programmers.voucher.global.io.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

import static com.programmers.voucher.global.util.ConsoleErrorMessages.INVALID_VOUCHER_CONSOLE_COMMAND;

public enum VoucherCommandType implements CommandType {
    CREATE("create"),
    LIST("list"),
    HELP("help");

    private static final Logger LOG = LoggerFactory.getLogger(VoucherCommandType.class);

    private final String type;

    VoucherCommandType(String type) {
        this.type = type;
    }

    public static VoucherCommandType getValue(String type) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, type))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = String.format(INVALID_VOUCHER_CONSOLE_COMMAND, type);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    @Override
    public String getType() {
        return type;
    }
}
