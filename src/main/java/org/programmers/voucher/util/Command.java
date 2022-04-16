package org.programmers.voucher.util;

public enum Command {
    EXIT("exit", "exit the program."),
    CREATE("create", "create a new voucher."),
    LIST("list", "list all vouchers.");

    private String command;
    private String explanation;

    Command(String command, String explanation){
        this.command = command;
        this.explanation = explanation;
    }

    @Override
    public String toString(){
        return command;
    }

    public String getExplanation(){
        return explanation;
    }
}
