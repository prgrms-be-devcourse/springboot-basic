package com.kdt.commandLineApp;

import com.kdt.commandLineApp.exception.WrongCommandException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum Command {
    BLACKLIST("blacklist"),
    CREATE("create"),
    EXIT("exit"),
    LIST("list"), 
    GIVE_VOUCHER("give voucher"),
    TAKE_VOUCHER("take voucher"),
    CUSTOMER_LIST_WITH_SAME_VOUCHER("customer list"),
    VOUCHER_LIST_OF_CUSTOMER("voucher list");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    public static Map<String, Command> commandMap = Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    public static Command fromString(String command) throws WrongCommandException {
        return Optional.ofNullable(commandMap.get(command)).orElseThrow(() -> new WrongCommandException());
    }
}