package com.prgms.management.command.io;

public enum CommandType {
    CREATE, LIST, EXIT, BLACKLIST, NONE;

    public static CommandType of(String command) {
        switch (command.toLowerCase()) {
            case "list":
                return LIST;
            case "create":
                return CREATE;
            case "exit":
                return EXIT;
            case "blacklist":
                return BLACKLIST;
            default:
                return NONE;
        }
    }
}
