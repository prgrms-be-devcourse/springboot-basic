package org.prgrms.kdt.common.dto;

import org.springframework.http.HttpStatus;

import java.util.Optional;

public class ResponseDto {
    private int status;
    private String message;
    private Object data;

    public ResponseDto(int status, ResponseMessage message) {
        this.status = status;
        this.message = message.getMessage();
    }

    public ResponseDto(int status, ResponseMessage message, Object data) {
        this.status = status;
        this.message = message.getMessage();
        this.data = data;
    }

    public static ResponseDto of(HttpStatus status, ResponseMessage message) {
        return new ResponseDto(status(status), message);
    }

    public static ResponseDto of(HttpStatus status, ResponseMessage message, Object data) {
        return new ResponseDto(status(status), message, data);
    }

    private static int status(HttpStatus status) {
        return Optional
                .ofNullable(status)
                .orElse(HttpStatus.OK)
                .value();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
