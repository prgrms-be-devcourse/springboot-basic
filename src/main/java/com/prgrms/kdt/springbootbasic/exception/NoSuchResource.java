package com.prgrms.kdt.springbootbasic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
public class NoSuchResource extends RuntimeException {
    public NoSuchResource(String s) {
    }
}
