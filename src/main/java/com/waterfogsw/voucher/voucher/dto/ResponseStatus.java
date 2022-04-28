package com.waterfogsw.voucher.voucher.dto;

public enum ResponseStatus {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String code;

    ResponseStatus(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
