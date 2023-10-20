package com.programmers.springbootbasic.mediator;

import java.util.Optional;

public class ConsoleResponse<T> {
    private T body;
    private String message;

    public ConsoleResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public ConsoleResponse(String message) {
        this.message = message;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(this.message);
    }

    public Optional<T> getBody() {
        return Optional.ofNullable(this.body);
    }
}
