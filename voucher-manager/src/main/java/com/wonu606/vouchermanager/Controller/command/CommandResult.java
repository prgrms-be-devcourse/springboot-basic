package com.wonu606.vouchermanager.Controller.command;

import java.util.Optional;

public class CommandResult {

    private final boolean continuing;
    private Optional<String> exceptionMessage = Optional.empty();

    public CommandResult() {
        continuing = true;
    }

    public CommandResult(boolean Continuing) {
        this.continuing = Continuing;
    }

    public boolean isContinuing() {
        return continuing;
    }

    public Optional<String> getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = Optional.of(exceptionMessage);
    }
}
