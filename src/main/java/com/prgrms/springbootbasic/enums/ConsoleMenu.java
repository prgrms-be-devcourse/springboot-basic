package com.prgrms.springbootbasic.enums;

public enum ConsoleMenu {
    CUSTOMER,
    VOUCHER,
    EXIT;

    public static ConsoleMenu of(String consoleMenu) {
        try {
            return ConsoleMenu.valueOf(consoleMenu.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다! 실행하고자 하는 프로그램의 메뉴를 확인 해주세요!");
        }
    }
}
