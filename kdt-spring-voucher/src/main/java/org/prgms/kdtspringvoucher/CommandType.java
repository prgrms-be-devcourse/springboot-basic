package org.prgms.kdtspringvoucher;

public enum CommandType {
    EXIT("exit"),
    CREAT("create"),
    LIST("list"),
    BLACK("black");

    private String commandType;

    CommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }
}
