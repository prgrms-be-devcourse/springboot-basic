package prgms.vouchermanagementapp.io.message;

public enum SystemMessage {

    EXIT("프로그램이 종료됩니다.");

    private final String message;

    SystemMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
