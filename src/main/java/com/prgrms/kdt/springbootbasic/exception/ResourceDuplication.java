package com.prgrms.kdt.springbootbasic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Already exists")
public class ResourceDuplication extends RuntimeException {
    public ResourceDuplication(String s) {
    }
}
