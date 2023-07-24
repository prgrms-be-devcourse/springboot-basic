package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.enums.StatusCode;

public class ResponseDTO<T> {
    private T data;
    private StatusCode statusCode;

    public ResponseDTO(T data, StatusCode statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }
}
