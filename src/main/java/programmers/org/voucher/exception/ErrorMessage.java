package programmers.org.voucher.exception;

public enum ErrorMessage {
    COMMAND_ERROR_MESSAGE("유효하지 않은 커맨드입니다."),
    VOUCHER_ERROR_MESSAGE("유효하지 않은 바우처 타입입니다."),
    DISCOUNT_ERROR_MESSAGE("유효한 할인 범위가 아닙니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
