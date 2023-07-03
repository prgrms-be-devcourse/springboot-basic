package org.programmers.VoucherManagement.global.exception.file;

public enum FileExceptionMessage {
    NOT_EXIST_FILE("해당하는 파일을 찾을 수 없습니다."),
    CAN_NOT_READ_LINE("파일을 읽을 수 없습니다.");

    private final String message;

    FileExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
