package com.prgrms.voucher_manage.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ConsoleMessage {
    VOUCHER_FIND_ID("찾으려는 바우처 아이디를 입력해주세요."),
    VOUCHER_UPDATE_ID("업데이트하려는 바우처 id를 입력해주세요."),
    VOUCHER_UPDATE_PRICE("업데이트하려는 바우처 가격을 입력해주세요."),
    VOUCHER_DELETE_ID("삭제하려는 바우처 아이디를 입력해주세요."),

    CUSTOMER_SAVE_NAME("저장할 회원의 이름을 입력해주세요."),
    CUSTOMER_UPDATE_NAME("업데이트할 회원의 이름을 입력해주세요."),
    CUSTOMER_UPDATE_ID("업데이트할 회원의 아이디를 입력해주세요."),
    CUSTOMER_FIND_NAME("검색할 회원 이름을 입력해주세요.");


    private final String message;

}
