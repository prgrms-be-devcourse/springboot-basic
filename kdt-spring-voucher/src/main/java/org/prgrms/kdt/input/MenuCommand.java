package org.prgrms.kdt.input;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MenuCommand {
    EXIT("exit"), CREATE("create"), LIST("list");
    private final String command;
    private static final Map<String, MenuCommand> MENU_COMMAND_MAP = Stream.of(values()).collect(Collectors.toMap(c -> c.command, c -> c));

    MenuCommand(String command) {
        this.command = command;
    }
    public static MenuCommand findByUserInputMenuCommand(String menuCommand){
        return MENU_COMMAND_MAP.get(menuCommand);
    }
}
