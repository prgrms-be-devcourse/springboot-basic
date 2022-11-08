package com.example.springbootbasic.console.output;

import com.example.springbootbasic.console.ConsoleStatus;

import static com.example.springbootbasic.console.ConsoleStatus.SUCCESS;
import static com.example.springbootbasic.util.CharacterUnit.EMPTY;

public class ResponseBody {
    private ConsoleStatus status = SUCCESS;
    private String body = EMPTY.getUnit();

    public ConsoleStatus getStatus() {
        return status;
    }

    public void setStatus(ConsoleStatus status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}