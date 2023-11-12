package com.zerozae.voucher.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder

public record ErrorResponse(
        @JsonIgnore
        Integer status,
        String code,
        String message){

    public static ResponseEntity<Object> toResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.status())
                .body(
                        ErrorResponse.builder()
                                .code(errorResponse.code)
                                .message(errorResponse.message())
                                .build()
                );
    }
}
