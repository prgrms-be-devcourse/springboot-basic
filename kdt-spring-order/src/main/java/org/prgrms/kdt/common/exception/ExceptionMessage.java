package org.prgrms.kdt.common.exception;

public enum ExceptionMessage {
    INVALID_VOUCHER_TYPE("유효하지 않은 바우처 유형 입니다."),
    INVALID_VOUCHER_PERCENT_RANGE("유효하지 않은 할인율을 입력하였습니다. 0~100 사이의 값을 입력하세요."),
    NUMBER_FORMAT_EXCEPTION("숫자 형식으로 입력하세요"),
    INVALID_COMMAND("유효하지 않은 커맨드 입니다."),
    VOUCHER_NOT_FOUNDED("바우처 정보가 존재하지 않습니다."),
    VOUCHER_FILE_NOT_FOUNDED("바우처 파일이 존재하지 않습니다."),
    VOUCHER_NOT_INSERTED("바우처 추가를 실패하였습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
