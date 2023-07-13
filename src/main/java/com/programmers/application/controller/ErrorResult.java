package com.programmers.application.controller;

public record ErrorResult(String errorCode, String errorMessage) {
    public static ErrorResult of(String errorCode, String errorMessage) {
        return new ErrorResult(errorCode, errorMessage);
    }
}
