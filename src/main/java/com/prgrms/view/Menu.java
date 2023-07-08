package com.prgrms.view;

import com.prgrms.view.command.Command;
import com.prgrms.view.command.CommandFactory;
import com.prgrms.view.message.ErrorMessage;

import java.util.Arrays;
import java.util.function.Function;

public enum Menu {
    EXIT(CommandFactory::createExitCommand),
    CREATE(CommandFactory::createCreateCommand),
    LIST(CommandFactory::createListCommand);

    private final Function<CommandFactory, Command> commandFunction;

    Menu(Function<CommandFactory, Command> commandFunction) {
        this.commandFunction = commandFunction;
    }

    public static Menu findByMenu(String menu) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public Command createCommand(CommandFactory commandFactory) {
        return commandFunction.apply(commandFactory);
    }
}
