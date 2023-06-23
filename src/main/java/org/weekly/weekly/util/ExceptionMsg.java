package org.weekly.weekly.util;

public enum ExceptionMsg {
    EMPTY("사용자가 아무 값도 입력하지 않았습니다."),
    NOT_INPUT_FORMAT("입력 형식에 맞지 않습니다."),
    NOT_MENU("해당 메뉴는 존재하지 않습니다.");


    private String msg;

    ExceptionMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
