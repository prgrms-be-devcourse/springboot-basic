package com.programmers.springbootbasic.mediator;

import java.util.Optional;

public class ConsoleRequest<T> {
    private String command;
    private T body;

    public ConsoleRequest(String command) {
        this.command = command;
    }

    public ConsoleRequest(String command,T body) {
        this.command = command;
        this.body = body;
    }

    public Optional<T> getBody() {
        return Optional.ofNullable(body);
    }

    public String getCommand() {
        return command;
    }
}
