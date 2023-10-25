package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;

public enum UseStatusType {

    AVAILABLE("사용 가능"),
    UNAVAILABLE("사용 불가능");

    private final String description;

    UseStatusType(String status) {
        this.description = status;
    }

    public String getDescription() {
        return description;
    }

    public static UseStatusType of(String input) {
        try {
            return UseStatusType.valueOf(input.toUpperCase());
        }catch (RuntimeException e) {
            throw ErrorMessage.error("존재하지 않는 사용 타입입니다. 다시 입력해주세요.");
        }
    }
}
