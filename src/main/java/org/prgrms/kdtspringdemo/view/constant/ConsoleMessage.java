package org.prgrms.kdtspringdemo.view.constant;

public class ConsoleMessage {
    public static final String MAIN_PROGRAM_INIT_MESSAGE = """
            === Voucher Program ===
            EXIT -> 프로그램 종료
            VOUCHER -> 바우처 관련 서비스
            CUSTOMER -> 소비자 관련 서비스
            """;
    public static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";
    public static final String VOUCHER_SERVICE_INIT_MESSAGE = """
            === Voucher Program ===
            CREATE -> 바우처 생성
            LIST -> 특정 바우처 조회
            LIST_ALL -> 바우처 전체 조회
            UPDATE -> 특정 바우처 업데이트
            DELETE -> 특정 바우처 삭제
            """;
    public static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";
    public static final String AMOUNT_VOUCHER_MESSAGE = "할인 금액을 입력하세요.\n";
    public static final String SUCCESS_CREATED_VOUCHER = """
            type : %s
            discount amount : %s
            """;
}
