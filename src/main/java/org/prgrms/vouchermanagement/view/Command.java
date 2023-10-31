package org.prgrms.vouchermanagement.view;

public enum Command {
    VOUCHER, // VOUCHER 메뉴
    CUSTOMER, // CUSTOMER 메뉴 이동
    WALLET, // WALLET 메뉴로 이동
    EXIT, // 종료

    CREATE, // 바우처 생성, 지갑 생성
    LIST, // 바우처 리스트, 고객 리스트
    BLACKLIST, // 고객 블랙 리스트
    UPDATE, //바우처 업데이트
    DELETE, //바우처 전부 삭제, 지갑 삭제
    FIND // 바우처 id로 고객 찾기
}
