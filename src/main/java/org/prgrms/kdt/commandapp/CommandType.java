package org.prgrms.kdt.commandapp;


import java.util.*;


public enum CommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    INVALID("invalid");

    private static final Map<String, CommandType> typeByName = new HashMap<>(CommandType.values().length);

    static {
        for (CommandType type : CommandType.values()) {
            typeByName.put(type.getName(), type);
        }
    }

    private final String name;

    CommandType(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public static CommandType lookup(String name) {
        return typeByName.getOrDefault(name, CommandType.INVALID);
    }
}