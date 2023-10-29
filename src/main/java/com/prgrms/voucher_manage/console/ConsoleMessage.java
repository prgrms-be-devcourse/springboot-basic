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
    CUSTOMER_FIND_NAME("검색할 회원 이름을 입력해주세요."),

    WALLET_SAVE_CUSTOMER_ID("지갑을 저장할 회원의 아이디를 입력해주세요."),
    WALLET_SAVE_VOUCHER_ID("지갑에 저장할 쿠폰 아이디를 입력해주세요."),

    WALLET_FIND_CUSTOMER_ID("쿠폰을 조회할 회원 아이디를 입력해주세요."),
    WALLET_FIND_VOUCHER_ID("쿠폰 아이디를 입력해주세요."),
    WALLET_DELETE_CUSTOMER_ID("삭제할 지갑의 회원 아이디를 입력해주세요"),
    WALLET_DELETE_VOUCHER_ID("삭제할 지갑의 쿠폰 아이디를 입력해주세요");


    private final String message;

}
