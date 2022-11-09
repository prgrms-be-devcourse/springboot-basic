package com.example.springbootbasic.console.input;

import com.example.springbootbasic.console.ResponseType;
import com.example.springbootbasic.console.console.ConsoleType;

import static com.example.springbootbasic.console.ResponseType.SUCCESS;
import static com.example.springbootbasic.util.CharacterUnit.EMPTY;

public class RequestBody {
    private ResponseType status = SUCCESS;
    private ConsoleType consoleType;
    private String body = EMPTY.getUnit();

    public ResponseType getStatus() {
        return status;
    }

    public void setStatus(ResponseType status) {
        this.status = status;
    }

    public ConsoleType getConsoleType() {
        return consoleType;
    }

    public void setConsoleType(ConsoleType consoleType) {
        this.consoleType = consoleType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}