package prgms.vouchermanagementapp.io.message;

public enum ExceptionMessage {

    NO_MENU_EXISTS("존재하지 않는 커맨드입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
