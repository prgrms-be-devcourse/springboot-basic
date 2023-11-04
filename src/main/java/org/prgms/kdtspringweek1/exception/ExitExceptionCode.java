package org.prgms.kdtspringweek1.exception;

public enum ExitExceptionCode {
    FAIL_TO_LOAD_DATA("[System] 데이터 불러오기에 실패하였습니다.");

    private String message;

    ExitExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
