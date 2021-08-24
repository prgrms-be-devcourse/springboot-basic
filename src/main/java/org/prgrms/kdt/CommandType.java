package org.prgrms.kdt;

public enum CommandType {
    CREATE, LIST, EXIT;

    public static boolean has(String command) {
        for(CommandType type : CommandType.values()) {
            if (type.name().equals(command))
                return true;
        }
        return false;
    }

}
