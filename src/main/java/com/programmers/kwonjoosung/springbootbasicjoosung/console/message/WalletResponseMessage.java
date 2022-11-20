package com.programmers.kwonjoosung.springbootbasicjoosung.console.message;

public enum WalletResponseMessage implements ResponseMessage {
    INSERT_WALLET_SUCCESS("해당 Voucher를 해당 Customer의 지갑에 추가했습니다."),
    INSERT_WALLET_FAIL("해당 Voucher를 해당 Customer의 지갑에 추가하지 못했습니다."),
    DELETE_WALLET_SUCCESS("해당 Voucher를 해당 Customer의 지갑에서 삭제하였습니다."),
    DELETE_WALLET_FAIL("해당 Voucher를 해당 Customer의 지갑에서 삭제하지 못했습니다.");

    private final String message;

    WalletResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
