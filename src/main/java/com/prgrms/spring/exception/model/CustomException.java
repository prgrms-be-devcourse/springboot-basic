package com.prgrms.spring.exception.model;

import com.prgrms.spring.exception.Error;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final Error error;

    public CustomException(Error error, String message) {
        super(message);
        this.error = error;
    }
}

