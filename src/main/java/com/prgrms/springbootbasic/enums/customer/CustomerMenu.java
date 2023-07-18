package com.prgrms.springbootbasic.enums.customer;

public enum CustomerMenu {
    CREATE,
    UPDATE,
    DELETE,
    SELECT;

    public static CustomerMenu of(String menu) {
        try {
            return valueOf(menu.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다! 실행하고자 하는 고객 프로그램의 메뉴를 다시 확인해주세요!");
        }
    }

}
