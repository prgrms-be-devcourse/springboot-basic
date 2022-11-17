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
    FILE_READ_ERROR("파일 읽기에 문제가 발생했습니다.\n"),
    CONSUMER_FILE_READ_ERROR("블랙리스트 읽기에 문제가 발생했습니다.\n"),
    READ_LINE("입력에 문제가 발생했습니다."),
    VALIDATE_PARSE_TO_NUMBER_ERROR("잘못된 입력 값 입니다. 정상 입력은 숫자 입니다."),
    FILE_VOUCHER_REPOSITORY_SAVE_ERROR("FileVoucherRepository save 에러발생"),
    FILE_VOUCHER_REPOSITORY_FINDALL_ERROR("FileVoucherRepository findAll 에러발생"), MENU_TOCODE_ERROR("메뉴선택에서 문제가 발생하였습니다.");
    ;

    private String message;

    Message(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
