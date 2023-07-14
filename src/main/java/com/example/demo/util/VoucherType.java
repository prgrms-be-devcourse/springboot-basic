package com.example.demo.util;

public enum VoucherType {
    FIX("    할인 금액을 입력해주세요 >> "),
    PERCENT("    1 이상 100 이하로 입력해주세요.\n    할인 비율을 입력해주세요 >> ");


    private final String voucherAmountInfoMessage;

    VoucherType(String voucherAmountInfoMessage) {
        this.voucherAmountInfoMessage = voucherAmountInfoMessage;
    }

    public String getVoucherAmountInfoMessage() {
        return voucherAmountInfoMessage;
    }

    public static VoucherType from(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 " + input + "는 유효한 바우처 종류가 아닙니다.\n fix 또는 percent를 입력하세요.\n");
        }
    }
}
