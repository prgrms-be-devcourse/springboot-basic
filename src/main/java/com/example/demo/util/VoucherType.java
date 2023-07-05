package com.example.demo.util;

public enum VoucherType {
    FIX("fix", "할인 금액을 입력해주세요 >> "),
    PERCENT("percent", "    1 이상 100 이하로 입력해주세요.\n    할인 비율을 입력해주세요 >> ");


    private final String voucherType;
    private final String voucherAmountInfoMessage;

    VoucherType(String voucherType, String voucherAmountInfoMessage) {
        this.voucherType = voucherType;
        this.voucherAmountInfoMessage = voucherAmountInfoMessage;
    }

    public String getVoucherAmountInfoMessage() {
        return voucherAmountInfoMessage;
    }

    @Override
    public String toString() {
        return voucherType;
    }
    
    public static VoucherType from(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 " + input + "는 유효한 바우처 종류가 아닙니다.\n fix 또는 percent를 입력하세요.\n");
        }
    }
}
