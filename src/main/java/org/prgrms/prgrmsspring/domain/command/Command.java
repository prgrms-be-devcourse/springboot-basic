package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.controller.ApplicationController;

public interface Command {

    String getDocument();

    static <T extends Enum<T> & Command> Command from(String name, Class<T> enumClass) {
        return Enum.valueOf(enumClass, name.toUpperCase());
    }

    void run(ApplicationController controller);
}
