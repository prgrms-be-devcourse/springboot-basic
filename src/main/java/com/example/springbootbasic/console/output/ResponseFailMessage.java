package com.example.springbootbasic.console.output;


public enum ResponseFailMessage {
    RESPONSE_ERROR("응답 도중 에러가 발생하였습니다."),
    RESPONSE_EXIT("바우처 관리 애플리케이션을 종료합니다."),
    VOUCHER_EMPTY_LIST_ERROR("생성된 바우처가 없습니다."),
    VOUCHER_REQUEST_FAIL_ERROR("실패된 요청값 입니다."),
    VOUCHER_FIND_EMPTY_ERROR("입력한 바우처는 존재하지 않습니다.");

    private final String message;

    ResponseFailMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
