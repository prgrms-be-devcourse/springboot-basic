package org.prgrms.prgrmsspring.console;


import org.prgrms.prgrmsspring.domain.command.Command;

import java.util.Arrays;
import java.util.List;

public interface ConsoleIOManager {

    void run(String commandName);

    void printCommands();

    default List<String> getModeStrings(Enum<?>[] enumValues) {
        return Arrays.stream(enumValues)
                .map(v -> "Type " + v.name() + " to " + ((Command) v).getDocument())
                .toList();
    }
}
