package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;

public enum Menu {
    EXIT(1),
    CREATE(2),
    LIST(3),
    BLACK_LIST(4),
    CREATE_MEMBER(5),
    ASSIGN_VOUCHER(6),
    VOUCHER_LIST_BY_MEMBER(7),
    DELETE_WALLET(8),
    MEMBER_LIST_BY_VOUCHER(9);

    private final int descriptonNumber;

    Menu(int descriptonNumber) {
        this.descriptonNumber = descriptonNumber;
    }

    public static Menu getMenu(int num) {
        return Arrays.stream(Menu.values())
                .filter((e) -> e.descriptonNumber == num)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("잘못된 입력입니다."));
    }

    public boolean isNotExit() {
        return this != EXIT;
    }
}
