package org.prgrms.voucherapplication.global.exception;

public enum ExceptionCode {

    // 파일
    FILE_IO_ERROR(500, "csv 파일 처리 과정에서 오류가 발생했습니다. 확인 후 프로그램을 재실행 시켜주세요."),
    FILE_DELETE_FAIL(500, "파일 삭제가 실패했습니다."),

    // customer
    NAME_NOT_BLANK(400, "이름은 빈값이 불가능합니다."),

    // voucher
    NOT_EXIST(400, "없는 바우처 종류입니다."),

    // DB
    NOTHING_INSERT(400, "Nothing was inserted"),
    EMPTY_RESULT(404, "Got empty result");

    int status;
    String messege;

    ExceptionCode(int status, String messege) {
        this.status = status;
        this.messege = messege;
    }

    public String getMessege() {
        return messege;
    }

    public int getStatus() {
        return status;
    }
}
