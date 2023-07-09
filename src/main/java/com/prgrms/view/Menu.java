package com.prgrms.view;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.command.Command;
import com.prgrms.view.command.CreateCommand;
import com.prgrms.view.command.ExitCommand;
import com.prgrms.view.command.ListCommand;
import com.prgrms.view.message.ErrorMessage;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum Menu {
    EXIT(ExitCommand::new),
    CREATE(CreateCommand::new),
    LIST(ListCommand::new);

    private final BiFunction<VoucherController, ViewManager, Command> commandFunction;

    Menu(BiFunction<VoucherController, ViewManager, Command> commandFunction) {
        this.commandFunction = commandFunction;
    }

    public static Menu findByMenu(String menu) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public Command createCommand(VoucherController voucherController, ViewManager viewManager) {
        return commandFunction.apply(voucherController, viewManager);
    }
}
