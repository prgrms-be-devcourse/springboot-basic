package com.programmers.vouchermanagement.common;

public enum WalletMessage {
    WALLET_COMMAND_LIST_MESSAGE(""" 
            === Wallet Management ===
            0. 뒤로 가기
            1. 고객이 보유한 바우처 조회
            2. 특정 바우처를 보유한 고객 조회
            3. 고객에게 바우처 지급
            4. 고객에게 지급한 바우처 삭제
            """),
    WALLET_CREATED_MESSAGE("[System] Wallet created."),
    WALLET_DELETED_MESSAGE("[System] Wallet deleted."),
    WALLET_GIVEN_SUCCESS_MESSAGE("[System] Wallet given.");

    private final String message;

    WalletMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
