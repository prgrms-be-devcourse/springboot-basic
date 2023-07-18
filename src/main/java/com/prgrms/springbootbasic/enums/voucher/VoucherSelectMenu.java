package com.prgrms.springbootbasic.enums.voucher;

//모든 바우처를 조회하려면 All,타입별로 조회를 하려면 Type, ID로 조회하려면 ID, 생성일로 조회하려면 CreateAt을 입력해주세요
public enum VoucherSelectMenu {
    ID,
    TYPE,
    CREATEDAT,
    ALL;

    public static VoucherSelectMenu of(String readMenu) {
        try {
            return valueOf(readMenu.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다! 실행하고자 하는 바우처 프로그램의 명령어를 다시 확인 해주세요!");
        }
    }
}
