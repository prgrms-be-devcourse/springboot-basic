package com.prgrms.spring.exception.model;

import com.prgrms.spring.exception.Error;

public class NotFoundException extends CustomException {

    public NotFoundException(Error error, String message) {
        super(error, message);
    }
}
