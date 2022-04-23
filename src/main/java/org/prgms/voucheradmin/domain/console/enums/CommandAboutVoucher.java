package org.prgms.voucheradmin.domain.console.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandAboutVoucher {
    CREATE("1", "create voucher"),
    READ("2", "list all vouchers"),
    UPDATE("3", "update voucher"),
    DELETE("4", "delete voucher");


    private final String commandId;
    private final String commandName;
    private static final Map<String, CommandAboutVoucher> commandsAboutVoucher =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(commandAboutVoucher -> commandAboutVoucher.commandId, Function.identity())));

    CommandAboutVoucher(String commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return String.format("%s. %s", commandId, commandName);
    }

    public static Optional<CommandAboutVoucher> findCommandAboutVoucher(String selectedCommandId) {
        return Optional.ofNullable(commandsAboutVoucher.get(selectedCommandId));
    }
}
