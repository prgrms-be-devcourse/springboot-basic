package com.programmers.springbootbasic.mediator;

import java.util.Optional;

public class ConsoleRequest<T> {

    private final String command;
    private final T body;

    public ConsoleRequest(String command) {
        this(command, null);
    }

    public ConsoleRequest(String command, T body) {
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
