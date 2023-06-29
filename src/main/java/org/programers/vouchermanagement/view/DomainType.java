package org.programers.vouchermanagement.view;

import java.util.Arrays;

public enum DomainType {
    VOUCHER(1), MEMBER(2), WALLET(3), EXIT(4);

    private final int number;

    DomainType(int number) {
        this.number = number;
    }

    public static DomainType from(int number) {
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

    public boolean isWallet() {
        return this == WALLET;
    }

    public boolean isExit() {
        return this == EXIT;
    }

    public int getNumber() {
        return number;
    }
}
