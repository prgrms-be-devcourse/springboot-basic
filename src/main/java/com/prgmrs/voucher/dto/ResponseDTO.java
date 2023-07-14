package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.enums.ErrorCode;

public class ResponseDTO<T> {


    private T data;
    private ErrorCode errorCode;

    public ResponseDTO(T data, ErrorCode errorCode) {
        this.data = data;
        this.errorCode = errorCode;
    }

    public boolean isError() {
        if(!errorCode.equals(ErrorCode.REQUEST_SUCCESS)) {
            return true;
        }
        return false;
    }

    public T getData() {
        return data;
    }
}
