package com.prgms.kdtspringorder.ui.message;

public enum InputMessage {
    ENTER_COMMAND("\n명령어를 입력하세요."),
    ENTER_VOUCHER_TYPE("바우처 종류: Fixed 와 Percent 중 하나를 입력하세요:"),
    ENTER_DISCOUNT_AMOUNT("할인가격(률)을 입력하세요:");

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
