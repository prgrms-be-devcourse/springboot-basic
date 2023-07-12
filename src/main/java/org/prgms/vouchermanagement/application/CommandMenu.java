package org.prgms.vouchermanagement.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum CommandMenu {
    EXIT("exit"),
    CREATE_NEW_VOUCHER("create"),
    SHOW_VOUCHER_LIST("list"),
    SHOW_BLACK_LIST("black"),
    SHOW_CUSTOMER_LIST("customers");

    private final String command;

    public static final Map<String, CommandMenu> COMMAND_MENU_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(CommandMenu::getCommand, Function.identity())));

    public static CommandMenu getCommandMenu(String input) {
        if (COMMAND_MENU_MAP.containsKey(input)) {
            return COMMAND_MENU_MAP.get(input);
        }
        throw new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
    }
}
