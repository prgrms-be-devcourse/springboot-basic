package org.prgms.voucheradmin.domain.console.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandAboutCustomer {
    CREATE("1", "create customer"),
    READ("2", "list all customers"),
    UPDATE("3", "update customer"),
    DELETE("4", "delete customer");

    private final String commandId;
    private final String commandName;
    private static final Map<String, CommandAboutCustomer> commandsAboutCustomer =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(commandAboutCustomer -> commandAboutCustomer.commandId, Function.identity())));

    CommandAboutCustomer(String commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return commandId+". "+commandName;
    }

    public static Optional<CommandAboutCustomer> findCommandAboutCustomer(String selectedCommandId) {
        return Optional.ofNullable(commandsAboutCustomer.get(selectedCommandId));
    }
}
