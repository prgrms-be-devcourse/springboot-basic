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
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + ":잘못된 입력 값입니다."));
    }

    public String getINTRO() {
        return INTRO;
    }

    public long isValid(String inputAmount) {
        long amount = toLong(inputAmount);
        //FIXED일 경우 최대 10000원 할인 가정.
        //PERCENT일 경우 0~100 입력받아야 함.
        if (VoucherType.FIXED.equals(this))
            validFixed(amount);
        else
            validPercent(amount);
        return amount;
    }

    private void validFixed(long amount) {
        if (amount < 0 || amount > 10000) throw new NumberFormatException(VoucherType.class + ":0~10000 이내로 입력하세요");
    }

    private void validPercent(long amount) {
        if (amount < 0 || amount > 100) throw new NumberFormatException(VoucherType.class + ":0~100 이내로 입력하세요");
    }

    private static long toLong(String amount) {
        try {
            return Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(VoucherType.class + ":올바른 숫자 형식이 아닙니다.");
        }
    }
}
