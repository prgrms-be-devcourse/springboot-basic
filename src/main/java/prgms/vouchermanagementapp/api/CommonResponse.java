package prgms.vouchermanagementapp.api;

public class CommonResponse {

    private String message;

    public CommonResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
