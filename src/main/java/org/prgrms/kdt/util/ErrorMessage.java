package org.prgrms.kdt.util;

public enum ErrorMessage {
    FILE_ACCESS_ERROR("파일에 접근하다 에러가 발생하였습니다."),
    INVALID_INPUT("입력이 잘못되었습니다.");

    private final String massage;

    ErrorMessage(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }
}
