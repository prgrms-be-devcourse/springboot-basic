package org.prgms.kdtspringweek1.exception;

public enum DataExceptionCode {
    FAIL_TO_INSERT("[System] 데이터 추가에 실패하였습니다."),
    FAIL_TO_SELECT("[System] 데이터 읽기에 실패하였습니다."),
    FAIL_TO_UPDATE("[System] 데이터 변경에 실패하였습니다."),
    FAIL_TO_DELETE("[System] 데이터 삭제에 실패하였습니다.");

    private String message;

    DataExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
