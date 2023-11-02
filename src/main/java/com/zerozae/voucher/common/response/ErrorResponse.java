package com.zerozae.voucher.common.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }
}
