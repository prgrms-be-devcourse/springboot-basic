package com.prgms.vouchermanager.contorller.front;

public enum FrontMenuType {
    VOUCHER(1),CUSTOMER(2),END(3);

    private final int number;
    FrontMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
