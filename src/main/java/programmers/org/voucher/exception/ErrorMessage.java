package programmers.org.voucher.exception;

public enum ErrorMessage {
    COMMAND_ERROR_MESSAGE("유효하지 않은 커맨드입니다."),
    VOUCHER_ERROR_MESSAGE("유효하지 않은 바우처 타입입니다.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
