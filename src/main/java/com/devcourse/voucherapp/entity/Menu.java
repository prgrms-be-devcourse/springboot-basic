package com.devcourse.voucherapp.entity;

public enum Menu {
    CREATE("1", "새 할인권 생성"),
    LIST("2", "할인권 조회");

    private final String number;
    private final String name;

    Menu(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }
}
