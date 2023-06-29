package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum WalletReadOption {
    VOUCHER(1), MEMBER(2);

    private final int number;

    WalletReadOption(int number) {
        this.number = number;
    }

    public static WalletReadOption from(int number) {
        return Arrays.stream(values())
                .filter(menu -> menu.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 명령어 번호입니다. : " + number));
    }

    public boolean isVoucher() {
        return this == VOUCHER;
    }

    public boolean isMember() {
        return this == MEMBER;
    }

    public int getNumber() {
        return number;
    }
}
