package org.programmers.springboot.basic.util.response;

public enum ErrCode {

    NOT_FOUND(404, "존재하지 않는 리소스"),
    BAD_REQUEST(400, "유효하지 않은 타입"),
    INPUT_INVALID_VALUE(401, "유효하지 않은 범위"),
    INTERNAL_SERVER_ERROR(500, "서버 오류");

    private final int status;
    private final String message;

    ErrCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
