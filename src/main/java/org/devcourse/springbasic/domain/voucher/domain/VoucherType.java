package org.devcourse.springbasic.domain.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum VoucherType {
    FIXED_AMOUNT("금액 할인 바우처", "원"),
    PERCENT_DISCOUNT("비율 할인 바우처", "%");

    private final String voucherName;
    private final String unit;

    public static VoucherType findVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> (type.equalsIgnoreCase(voucherType.toString())))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다. 메뉴 번호를 숫자로 정확히 입력해주세요."));
    }
}
