package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.console.ConsoleIOManager;

public interface Command {

    String getDocument();

    void run(ConsoleIOManager consoleIOManager);

    static <T extends Enum<T> & Command> Command from(String name, Class<T> enumClass) {
        return Enum.valueOf(enumClass, name.toUpperCase());
    }
}
