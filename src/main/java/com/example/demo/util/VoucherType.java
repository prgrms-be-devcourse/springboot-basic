package com.example.demo.util;

public enum VoucherType {
    FIX,
    PERCENT;


    public static VoucherType find(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 " + input + "는 유효한 바우처 종류가 아닙니다.\n fix 또는 percent를 입력하세요.\n");
        }
    }
}
