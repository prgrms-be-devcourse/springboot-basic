package com.prgms.vouchermanager.contorller.voucher;

import java.util.Arrays;

public enum VoucherMenuType {
    CREATE(1), UPDATE(2), LIST(3), ONE(4), DELETE_ONE(5), DELETE_ALL(6);
    private final int number;

    VoucherMenuType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static VoucherMenuType fromValue(int menu) {

        return Arrays.stream(VoucherMenuType.values())
                .filter(voucherMenuType -> voucherMenuType.getNumber() == menu)
                .findFirst()
                .get();
    }
}

