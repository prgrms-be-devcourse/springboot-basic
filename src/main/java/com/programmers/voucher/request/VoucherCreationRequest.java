package com.programmers.voucher.request;

import java.util.Objects;

public class VoucherCreationRequest {
    private String type;
    private long amount;

    public VoucherCreationRequest(String type, long amount) {
        validateVoucherCreationRequest(type, amount);
        this.type = type;
        this.amount = amount;
    }

    private static void validateVoucherCreationRequest(String type, long amount) {
        if(Objects.isNull(type) || amount < 0) {
            throw new IllegalArgumentException("올바르지 않은 바우처 생성 요청입니다.");
        }
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
