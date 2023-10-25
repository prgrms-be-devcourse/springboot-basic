package com.programmers.springbootbasic.mediator;

import java.util.Optional;

public class ConsoleResponse<T> {

    private final String message;
    private final T body;

    private ConsoleResponse(String message) {
        this(null, message);
    }

    private ConsoleResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(this.message);
    }

    public Optional<T> getBody() {
        return Optional.ofNullable(this.body);
    }

    public static ConsoleResponse<Void> createNoBodyResponse(String message) {
        return new ConsoleResponse<>(message);
    }

    public static <T> ConsoleResponse<T> createWithBodyResponse(T body, String message) {
        return new ConsoleResponse<>(body, message);
    }
}
