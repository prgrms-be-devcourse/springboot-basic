package org.prgrms.kdt.app.configuration.io;

public enum SystemMessage {
    EXIT_PROGRAM("프로그램을 종료합니다."),
    CREATE_BOUCHER_TYPE("fixed, percent 중 어떤 타입의 바우처를 만드시겠습니까?"),
    CREATE_FIXED_BOUCHER("고정으로 할인되는 비용을 입력하세요."),
    CREATE_PERCENT_BOUCHER("할인되는 퍼센트를 정수로 입력하세요."),
    EXCEPTION_VOUCHER_TYPE("올바른 바우처 타입을 입력하세요."),
    EXCEPTION_FIXED_AMOUNT_MINUS("할인되는 비용이 0보다 커야합니다."),
    EXCEPTION_FIXED_AMOUNT_OVER("할인되는 비용이 너무 큽니다. 10만원 밑으로 설정해주세요."),
    EXCEPTION_PERCENT_MINUS("할인되는 퍼센트가 음수일 수 없습니다."),
    EXCEPTION_PERCENT_OVER("할인되는 퍼센트가 100을 넘을 수 없습니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    SystemMessage(String message) {
        this.message = message;
    }
}
