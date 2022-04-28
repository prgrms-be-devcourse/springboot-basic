package org.prgrms.kdt.domain.common.exception;

public enum ExceptionType {
    NOT_UPDATED("업데이트 된 컬럼이 없습니다."),
    NOT_DELETED("삭제된 컬럼이 없습니다."),
    NOT_SAVED("저장된 컬럼이 없습니다."),
    NOT_SUPPORTED("지원하지 않는 기능입니다.");

    private final String msg;

    ExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
