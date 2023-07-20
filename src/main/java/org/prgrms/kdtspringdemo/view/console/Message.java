package org.prgrms.kdtspringdemo.view.console;

public enum Message {
    MAIN_PROGRAM_INIT("""
                === Voucher Program ===
                EXIT -> 프로그램 종료
                VOUCHER -> 바우처 관련 서비스
                CUSTOMER -> 소비자 관련 서비스
                """),
    SYSTEM_SHUTDOWN("시스템을 종료합니다.\n"),
    VOUCHER_SERVICE_INIT("""
                === Voucher Program ===
                CREATE -> 바우처 생성
                FIND_ID -> 특정 바우처 조회
                FIND_ALL -> 바우처 전체 조회
                UPDATE -> 특정 바우처 업데이트
                DELETE -> 특정 바우처 삭제
                """),
    CHOICE_VOUCHER_TYPE("바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n"),
    AMOUNT_VOUCHER("할인 금액을 입력하세요.\n"),
    PRINT_VOUCHER_INFO("""
                id : %s
                type : %s
                discount amount : %s
                """),
    VOUCHER_ID("바우처 Id를 입력하세요.\n"),
    CUSTOMER_SERVICE_INIT("""
                === Voucher Program ===
                CREATE -> 소비자 생성
                FIND_ID -> 특정 소비자 ID로 조회
                FIND_NICKNAME -> 특정 소비자 닉네임으로 조회
                FIND_ALL -> 소비자 전체 조회
                UPDATE -> 특정 소비자 업데이트
                DELETE -> 특정 소비자 삭제
                """),
    INPUT_CUSTOMER_NICKNAME("닉네임을 입력해주세요.\n"),
    PRINT_CUSTOMER_INFO("""
                id : %s
                nickname : %s
                """),
    CUSTOMER_ID("소비자 Id를 입력하세요.\n")
    ;

    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
