package org.prgms.vouchermanagement.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CommandMenu {
    EXIT("exit"),
    CREATE_NEW_VOUCHER("create"),
    SHOW_VOUCHER_LIST("list"),
    SHOW_BLACK_LIST("black");

    private final String command;

    public static CommandMenu getCommandMenu(String input) {
        return Arrays.stream(CommandMenu.values())
                .filter(commandMenu -> commandMenu.getCommand().equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION));
    }
}
