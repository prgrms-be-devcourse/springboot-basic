package org.prgrms.kdt;

import java.util.Locale;

public enum CommandType {
    CREATE, LIST, EXIT;

    public static boolean has(String command) {
        for(CommandType type : CommandType.values()) {
            if (type.name().equals(command.toUpperCase()))
                return true;
        }
        return false;
    }

}
