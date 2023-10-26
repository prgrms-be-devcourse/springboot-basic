package org.prgms.kdtspringweek1.exception;

public enum FileExceptionCode {
    FAIL_TO_READ_DATA_FROM_CSV("[System] CSV 파일로부터 데이터를 읽어오는데 실패하였습니다."),
    FAIL_TO_UPDATE_DATA_ON_CSV("[System] CSV 파일의 데이터 수정을 실패하였습니다.");

    private String message;

    FileExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
