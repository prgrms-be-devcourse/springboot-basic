package com.prgrms.management.voucher.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherType {
    FIXED("할인 금액을 입력하세요.\nINPUT:"),
    PERCENT("할인율을 입력하세요.\nINPUT:");

    private final String INTRO;

    VoucherType(String intro) {
        INTRO = intro;
    }

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("잘못된 명령어입니다."));
    }

    public String getINTRO() {
        return INTRO;
    }

    public long isValid(String inputAmount) {
        long amount = toLong(inputAmount);
        //FIXED일 경우 최대 10000원 할인 가정.
        //PERCENT일 경우 0~100 입력받아야 함.
        boolean valid = (VoucherType.FIXED.equals(this)) ? validFixed(amount) : validPercent(amount);
        return amount;
    }

    private boolean validFixed(long amount) {
        if (amount < 0 || amount > 10000) throw new IllegalArgumentException();
        return true;
    }

    private boolean validPercent(long amount) {
        if (amount < 0 || amount > 100) throw new IllegalArgumentException();
        return true;
    }

    private long toLong(String amount) {
        try {
            return Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("올바른 숫자 형식이 아닙니다.");
        }
    }
}
