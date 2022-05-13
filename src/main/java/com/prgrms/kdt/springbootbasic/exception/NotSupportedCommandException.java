package com.prgrms.kdt.springbootbasic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Not Supported Command")
public class NotSupportedCommandException extends RuntimeException {
    public NotSupportedCommandException(String s, String command) {
    }
}
