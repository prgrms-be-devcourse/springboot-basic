package com.programmers.voucher.voucher;

import java.util.Arrays;
import java.util.List;

import static com.programmers.voucher.menu.Message.VOUCHER_INPUT_ERROR_MESSAGE;


public enum VoucherType {
    FixedAmount(List.of("F", "FixedAmount")),

    PercentDiscount(List.of("P", "PercentDiscount")),
    ;

    private final List<String> type;

    VoucherType(List<String> type) {
        this.type = type;
    }

    public List<String> getType() {
        return type;
    }

    public static VoucherType getValidateVoucherType(String inputType) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getType().contains(inputType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage()));
    }
}
