package org.prgms.voucher.ui;

public enum CommandType {
    EXIT("exit", 1),
    CREATE("create", 2),
    LIST("list", 3);

    private final String commandType;
    private final int choiceNumber;

    CommandType(String commandType, int choiceNumber) {
        this.commandType = commandType;
        this.choiceNumber = choiceNumber;
    }

    public String getCommandType() {
        return commandType;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }
    }
