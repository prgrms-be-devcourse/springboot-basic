package org.prgms.voucherProgram.global.exception;

import java.util.Date;

public class ExceptionResponse {
    private final Date date;
    private final String message;

    public ExceptionResponse(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
