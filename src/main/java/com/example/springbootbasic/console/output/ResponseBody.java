package com.example.springbootbasic.console.output;

import com.example.springbootbasic.console.ResponseType;
import com.example.springbootbasic.console.console.ConsoleType;

import static com.example.springbootbasic.console.ResponseType.*;
import static com.example.springbootbasic.util.CharacterUnit.EMPTY;

public class ResponseBody {
    private ResponseType status = FAIL;
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

    public boolean isSuccess() {
        return status == SUCCESS;
    }

    public boolean isEnd() { return status == END;}

    public boolean isFail() {
        return status == FAIL;
    }

    public boolean isAgain() {return status == AGAIN;}
}