package org.prgrms.prgrmsspring.exception;

public enum ExceptionMessage {
    NOT_FOUND_VOUCHER("해당 Voucher를 찾을 수 없습니다."),
    NOT_FOUND_CUSTOMER("해당 Customer를 찾을 수 없습니다."),
    NOT_FOUND_FILE("해당 파일을 찾을 수 없습니다."),
    INVALID_COMMAND_TYPE("적절하지 않은 커맨드 타입입니다."),
    NOT_FOUND_CONTROLLER_TYPE("해당 컨트롤 타입을 찾을 수 없습니다."),
    INSERT_QUERY_FAILED("INSERT 쿼리에 실패했습니다."),
    DELETE_QUERY_FAILED("DELETE 쿼리에 실패했습니다."),
    UPDATE_QUERY_FAILED("UPDATE 쿼리에 실패했습니다."),
    INVALID_NAME_INPUT("이름은 한글 또는 영어로만 입력되어야 합니다."),
    INVALID_EMAIL_INPUT("기본 이메일 형식에 맞춰 이메일 주소가 작성되어야 합니다.");
    private final String message;


    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
