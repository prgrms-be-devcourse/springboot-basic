package com.prgrms.kdt.springbootbasic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "DB error")
public class JdbcQueryFail extends RuntimeException  {
    public JdbcQueryFail(String s) {
    }
}
