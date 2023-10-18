package com.prgms.vouchermanager.contorller.customer;

public enum CustomerMenuType {
    BLACK_LIST(1);

    private final int number;

    CustomerMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
