package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.console.Command;

public abstract class Request {
    private final Command command;

    public Request(Command command) {
        this.command = command;
    }

    public Command command() {
        return command;
    }
}
