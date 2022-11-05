package com.example.springbootbasic.console.input;

import com.example.springbootbasic.console.ConsoleStatus;

import static com.example.springbootbasic.console.ConsoleStatus.SUCCESS;

public class RequestBody {
    private ConsoleStatus status = SUCCESS;

    private String body;

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
