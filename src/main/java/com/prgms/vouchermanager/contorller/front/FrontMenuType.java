package com.prgms.vouchermanager.contorller.front;

import java.util.Arrays;

public enum FrontMenuType {
    VOUCHER(1),CUSTOMER(2),WALLET(3),END(4);

    private final int number;
    FrontMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static FrontMenuType fromValue(int menu) {

        return Arrays.stream(FrontMenuType.values())
                .filter(frontMenuType -> frontMenuType.getNumber() == menu)
                .findFirst()
                .get();
    }
}
