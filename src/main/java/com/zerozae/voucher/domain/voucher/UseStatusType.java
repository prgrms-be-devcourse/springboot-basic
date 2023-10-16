package com.zerozae.voucher.domain.voucher;

public enum UseStatusType {
    AVAILABLE("사용 가능"),
    USED("사용 불가능");
    private final String description;
    UseStatusType(String status) {
        this.description = status;
    }
    public String getDescription(){
        return description;
    }
}
