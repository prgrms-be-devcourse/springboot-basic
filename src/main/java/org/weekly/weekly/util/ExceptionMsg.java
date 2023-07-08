package org.weekly.weekly.util;

public enum ExceptionMsg {
    ERROR("[ERROR]: "),
    UTIL_CLASS("유틸 클래스입니다."),
    EMPTY("사용자가 아무 값도 입력하지 않았습니다."),
    NOT_INPUT_FORMAT("입력 형식에 맞지 않습니다."),
    NOT_MENU("해당 메뉴는 존재하지 않습니다."),
    NOT_NUMBER_FORMAT("허용되지 않는 값입니다."),
    NOT_NATURAL_NUMBER("자연수가 아닙니다."),
    NOT_DISCOUNT("할인 종류가 아닙니다."),
    EXPIRATION_ERROR("유효기간이 등록기간보다 빠릅니다."),
    VOUCHER_EXIST("이미 존재하는 바우처입니다."),
    NOT_FOUND("해당 종류의 할인정보를 찾을 수 없습니다."),
    NOT_SAME_PARAM_SIZE("입력 파라미터 개수가 맞지 않습니다."),
    NOT_EMAIL_FORMAT("이메일형식이 아닙니다."),
    SQL_ERROR("값을 가져오기 실패"),
    SQL_INSERT_ERROR("값 추가 실패"),
    SQL_EXIST("이미 존재합니다."),
    SQL_DELETE_ERROR("삭제 실패");

    private String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return ERROR.msg+msg;
    }
}
