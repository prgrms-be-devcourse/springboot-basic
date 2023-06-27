package kr.co.programmers.school.voucher.domain.voucher.exception;

public enum ErrorMessage {
    NOT_FOUND_OPTION("[ERROR] 해당 명령이 존재하지 않습니다."),
    NOT_FOUND_VOUCHER_TYPE("[ERROR] 해당 바우처 타입이 존재하지 않습니다.")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}