package com.blessing333.springbasic.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotSupportedStrategyException extends RuntimeException{
    public NotSupportedStrategyException(String message) {
        super(message);
    }
}
