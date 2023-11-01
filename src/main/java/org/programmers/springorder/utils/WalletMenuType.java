package org.programmers.springorder.utils;

import org.programmers.springorder.constant.ErrorMessage;

import java.util.Arrays;

public enum WalletMenuType {
    ASSIGN("1"),
    VOUCHER("2"),
    DELETE("3"),
    CUSTOMER("4"),
    BACK("5");

    private final String menuNum;

    WalletMenuType(String menuNum) {
        this.menuNum = menuNum;
    }

    private boolean isEqual(String walletMenu) {
        return this.menuNum.equals(walletMenu);
    }

    public static WalletMenuType selectWalletMenu(String walletMenu) {
        return Arrays.stream(WalletMenuType.values())
                .filter(menu -> menu.isEqual(walletMenu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_VALUE_MESSAGE));
    }

}
