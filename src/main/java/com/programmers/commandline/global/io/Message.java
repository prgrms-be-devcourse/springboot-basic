package com.programmers.commandline.global.io;

public enum Message {
    SELECT_MENU("""                
            === 바우처 프로그램 ===
            
            1. 종료
            2. 쿠폰 생성
            3. 쿠폰 조회
            4. 악덕 소비자 조회

            입력: """),

    MENU_ERROR("잘못된 메뉴를 입력하셨습니다."),
    VOUCHER_MENU_ERROR("잘못된 바우처를 입력하셨습니다."),
    EXIT("프로그램을 종료합니다.\n"),
    SELECT_VOUCHER("""
                생성할 쿠폰 선택
                1. FixedAmountVoucher
                2. PercentDiscountVoucher
                """),
    DISCOUNT_AMOUNT("할인금액: "),
    DISCOUNT_RATE("할인율: "),
    DISCOUNT_ERROR("할인 적용을 잘못하셨습니다.\n"),
    FILE_READ_ERROR("파일 읽기에 문제가 발생했습니다.\n"),
    FILE_SAVE_ERROR("파일 쓰기에 문제가 발생하였습니다.\n"),
    COUSUMER_FILE_READ_ERROR("블랙리스트 읽기에 문제가 발생했습니다.\n");
    ;

    private String message;

    Message(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
