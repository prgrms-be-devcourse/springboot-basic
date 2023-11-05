package com.prgms.vouchermanager.contorller.wallet;

import java.util.Arrays;

import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_WALLET_MENU;

public enum WalletMenuType {
    CREATE(1), FIND_BY_CUSTOMER_ID(2), FIND_BY_VOUCHER_ID(3), DELETE_BY_CUSTOMER_ID(4);

    private final int number;

    WalletMenuType(int number) {
        this.number = number;
    }

    public static WalletMenuType fromValue(int menu) {

        return Arrays.stream(WalletMenuType.values())
                .filter(walletMenuType -> walletMenuType.getNumber() == menu)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(INVALID_WALLET_MENU.getMessage()));

    }

    public int getNumber() {
        return number;
    }
}
