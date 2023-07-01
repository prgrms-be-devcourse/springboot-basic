package org.promgrammers.springbootbasic.domain.wallet.model;

import java.util.Arrays;

public enum FindWalletType {

    WALLET_ID("1"),
    VOUCHER_ID("2"),
    CUSTOMER_ID("3");

    private final String type;


    FindWalletType(String type) {
        this.type = type;
    }

    public static FindWalletType from(String inputType) {
        return Arrays.stream(values())
                .filter(findWallet -> findWallet.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 선택 입니다. => " + inputType));
    }
}
