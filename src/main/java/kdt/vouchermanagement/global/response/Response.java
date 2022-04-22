package kdt.vouchermanagement.global.response;

public class Response {
    private final int statusCode;
    private final Object data;

    public Response(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static Response of(int statusCode, Object data) {
        return null;
    }
}
