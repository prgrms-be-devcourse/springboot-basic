package com.programmers.springbootbasic.util;

public enum Messages {

    SELECT_MENU("""
        === Voucher Program ===
        종료 : exit
               
        === Voucher Menu ===
        바우처 등록 : create voucher
        바우처 조회 : list voucher
        바우처 삭제 : delete voucher
        바우처 수정 : update voucher
                
        === User Menu ===
        유저 블랙리스트 조회 : list black user
        내 앞으로 바우처 등록 : register my voucher
        내 바우처 조회 : find my voucher
        내 바우처 삭제 : delete my voucher
        바우처를 소유한 유저 조회 : find users by voucher Id
        """),
    VOUCHER_REGISTER_TYPE("등록할 바우처의 타입을 입력하세요.(FIXED, PERCENT)"),
    VOUCHER_REGISTER_AMOUNT("등록할 바우처의 금액을 입력하세요.(숫자)"),
    INPUT_ID("ID를 입력하세요."),
    INPUT_NICKNAME("닉네임을 입력하세요."),
    VOUCHER_UPDATE_TYPE("수정할 바우처의 타입을 입력하세요.(FIXED, PERCENT)"),
    VOUCHER_UPDATE_AMOUNT("수정할 바우처의 금액을 입력하세요.(숫자)"),
    SUCCESS_VOUCHER_REGISTER("바우처가 등록되었습니다."),
    SUCCESS_VOUCHER_LIST("바우처 목록입니다."),
    SUCCESS_VOUCHER_FOUND("바우처 검색 결과 입니다."),
    SUCCESS_VOUCHER_DELETE("바우처가 삭제되었습니다."),
    SUCCESS_VOUCHER_UPDATE("바우처가 수정되었습니다."),
    SUCCESS_BLACK_USER_LIST("블랙리스트 유저 목록입니다."),
    SUCCESS_USER_VOUCHER_REGISTER("요청하신 바우처가 등록되었습니다."),
    SUCCESS_USER_FOUND_BY_VOUCHER("요청하신 바우처를 소유한 유저 목록입니다."),
    SUCCESS_USER_VOUCHER_FOUND_MINE("소유하신 바우처 목록입니다."),
    SUCCESS_USER_VOUCHER_DELETE("요청하신 바우처가 삭제되었습니다."),
    ;

    Messages(String Message) {
        this.message = Message;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}
