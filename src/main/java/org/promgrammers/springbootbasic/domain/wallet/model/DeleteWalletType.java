package org.promgrammers.springbootbasic.domain.wallet.model;

import java.util.Arrays;

public enum DeleteWalletType {

    WALLET_ID("1"),
    ALL("2");

    private final String type;


    DeleteWalletType(String type) {
        this.type = type;
    }

    public static DeleteWalletType from(String inputType) {
        return Arrays.stream(values())
                .filter(deleteWallet -> deleteWallet.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}
