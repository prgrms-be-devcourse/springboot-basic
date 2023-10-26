package org.prgrms.vouchermanagement.view;

public enum Command {
    CREATE, // 바우처 생성, 지갑 생성
    LIST, // 바우처 리스트, 고객의 바우처 전부 출력
    CUSTOMER, //고객 리스트
    BLACKLIST, // 블랙 리스트
    UPDATE, //바우처 업데이트
    DELETE, //바우처 전부 삭제, 지갑 삭제
    WALLET, // WALLET 메뉴로 이동
    FIND, // 바우처 id로 고객 찾기
    EXIT // 종료
}
