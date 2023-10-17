package com.zerozae.voucher.common.response;

import com.zerozae.voucher.common.message.MessageConverter;
import lombok.Getter;

import java.util.List;

@Getter
public class Response<T> {
    private boolean isSuccess;
    private String message;
    private List<T> data;

    public Response(boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Response(boolean isSuccess, List<T> data){
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public static Response success(){
        return new Response(true, MessageConverter.getMessage("SUCCESS.MSG"));
    }

    public static Response failure(String message){
        return new Response(false, message);
    }

    public static <T> Response success(List<T> data) {
        return new Response(true,  data);
    }
}

