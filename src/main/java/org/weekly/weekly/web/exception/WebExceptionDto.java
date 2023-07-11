package org.weekly.weekly.web.exception;

import org.weekly.weekly.customer.exception.CustomerException;

public class WebExceptionDto {
    private String message;
    public WebExceptionDto(CustomerException error) {
        this.message = error.getMessage();
    }

    public String getMessage() {
        return message;
    }
}
