package org.prgrms.kdtspringw1d1;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}