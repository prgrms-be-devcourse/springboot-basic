package org.prgms.voucherProgram.global.exception;

import java.util.Date;

public class ExceptionResponse {
    private final int code;
    private final Date date;
    private final String message;

    public ExceptionResponse(int code, Date date, String message) {
        this.code = code;
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
