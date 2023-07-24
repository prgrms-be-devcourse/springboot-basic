package org.weekly.weekly.global.handler;

public class WebExceptionDto {

    private final String message;

    public WebExceptionDto(RuntimeException error) {
        this.message = error.getMessage();
    }

    public String getMessage() {
        return message;
    }
}
