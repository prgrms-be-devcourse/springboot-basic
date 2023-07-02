package com.ray.junho.voucher.util;

import com.ray.junho.voucher.domain.Voucher;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> convertVouchersToList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
