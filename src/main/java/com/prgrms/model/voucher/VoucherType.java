package com.prgrms.model.voucher;

import com.prgrms.presentation.message.ErrorMessage;

import java.text.MessageFormat;
import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1",
            "얼만큼 할인 받고 싶은지 입력하세요 :"),
    PERCENT_DISCOUNT_VOUCHER("2",
            "할인율을 입력하세요 :");

    private final String number;
    private final String discountGuide;

    VoucherType(String number, String discountGuide) {
        this.number = number;
        this.discountGuide = discountGuide;
    }

    public static VoucherType findByType(String voucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(p -> p.number.equals(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_SELECTION.getMessage()));
    }

    public String voucherPolicyOptionGuide() {
        return MessageFormat.format("{0}번 : {1}", number, name());
    }

    public String discountGuide() {
        return discountGuide;
    }

}
