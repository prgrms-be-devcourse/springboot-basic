package org.prgms.kdtspringvoucher.commandLine;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    CUSTOMER("customer"),
    BLACK("black"),
    WALLET("wallet");

    private String commandType;

    CommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }
}
