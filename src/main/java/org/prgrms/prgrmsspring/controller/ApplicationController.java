package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.command.Command;

import java.util.Arrays;
import java.util.List;

public interface ApplicationController {
    void run(String commandName);

    void listMode();

    static List<String> getModeStrings(Enum<?>[] enumValues) {
        return Arrays.stream(enumValues)
                .map(v -> "Type " + v.name() + " to " + ((Command) v).getDocument())
                .toList();
    }

}
