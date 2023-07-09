package com.devcourse.voucherapp.entity;

import lombok.Getter;

@Getter
public enum CustomerType {
    NORMAL(1, "일반 고객"),
    BLACK(2, "블랙리스트 고객");

    private final int number;
    private final String name;

    CustomerType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
