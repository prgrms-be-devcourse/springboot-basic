package com.prgrms.springbootbasic.enums.customer;

public enum CustomerDeleteMenu {
    ID,
    ALL;

    public static CustomerDeleteMenu of(String deleteMenu) {
        try {
            return valueOf(deleteMenu.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다! 삭제하고자 하는 고객 프로그램의 명령어를 다시 확인 해주세요!");
        }
    }
}
