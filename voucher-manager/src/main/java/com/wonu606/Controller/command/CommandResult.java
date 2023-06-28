package com.wonu606.Controller.command;

public class CommandResult {

    private final boolean state;

    public CommandResult() {
        state = true;
    }

    public CommandResult(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }
}
