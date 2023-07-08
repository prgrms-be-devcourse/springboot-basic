package com.ray.junho.voucher.util;

public final class StringUtil {

    private StringUtil() {
        throw new RuntimeException("인스턴스를 생성할 수 없습니다!");
    }

    public static int convertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 값을 입력하셨습니다. 숫자를 입력해 주세요.", e);
        }
    }
}
