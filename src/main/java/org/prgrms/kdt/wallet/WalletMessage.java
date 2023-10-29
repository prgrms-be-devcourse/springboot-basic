package org.prgrms.kdt.wallet;

public enum WalletMessage {

    COMPLETE_ADD("지갑 추가가 완료되었습니다."),
    COMPLETE_REMOVE("지갑 제거가 완료되었습니다."),
    REMOVE_CUSTOMER_ID("바우처를 모두 제거할 고객의 아이디를 입력해주세요 : "),
    ADD_CUSTOMER_ID("바우처가 등록될 고객 아이디를 입력해주세요 : "),
    ADD_VOUCHER_ID("고객에게 등록할 바우처 아이디를 입력해주세요 : ");

    private final String message;

    public String getMessage() {
        return message;
    }

    WalletMessage(String message) {
        this.message = message;
    }
}
