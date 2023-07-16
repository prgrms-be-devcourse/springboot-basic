package com.prgrms.springbootbasic.enums;

public enum VoucherDeleteMenu {
    ID,
    ALL;

    public static VoucherDeleteMenu of(String deleteMenu) {
        try {
            return valueOf(deleteMenu.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다! 실행하고자 하는 바우처 명령어를 다시 확인 해주세요!");
        }
    }
}