package com.programmers.springbootbasic.mediator;

import java.util.Optional;

public class ConsoleResponse<T> {

    private final String message;
    private final T body;

    public ConsoleResponse(String message) {
        this(null, message);
    }

    public ConsoleResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(this.message);
    }

    public Optional<T> getBody() {
        return Optional.ofNullable(this.body);
    }
}
