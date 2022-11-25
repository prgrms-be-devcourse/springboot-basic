package com.programmers.commandline.global.io;

public enum Message {
    SELECT_MENU("""                
            === 바우처 프로그램 ===
                        
            1. 종료
            2. 쿠폰 생성
            3. 쿠폰 조회
            4. 악덕 소비자 조회
            5. 소비자 입력

            입력: """),

    MENU_ERROR("잘못된 메뉴를 입력하셨습니다."),
    VOUCHER_MENU_ERROR("잘못된 바우처를 입력하셨습니다."),
    EXIT("프로그램을 종료합니다.\n"),
    SELECT_VOUCHER("""
            생성할 쿠폰 선택
            1. FixedAmountVoucher
            2. PercentDiscountVoucher
            """),
    CONSUMER_FILE_READ_ERROR("블랙리스트 읽기에 문제가 발생했습니다.\n"),
    READ_LINE("입력에 문제가 발생했습니다."),
    VALIDATE_PARSE_TO_NUMBER_ERROR("잘못된 입력 값 입니다. 정상 입력은 숫자 입니다."),
    BAD_DISCOUNT("잘못된 할인 정책입니다."),
    INSERT_CONSUMER_NAME("이름 : "),
    INSERT_CONSUMER_EMAIL("이메일 : "),
    NULL_POINT("값이 없습니다."),
    CONSUMER_FILE_WRITE_ERROR("소비자를 파일에 저장하지 못했습니다."),
    INT_READ_EXCEPTION("int 타입 입력에서 에러가 발생하였습니다."),
    NULL_POINT_FILE("파일이 존제하지 않습니다."),
    OPTIONAL_NULL("조회결과가 없습니다.");
    private String message;

    Message(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
