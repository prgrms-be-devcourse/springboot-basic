package com.prgrms.spring.domain.voucher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum VoucherType {
    FIXED_AMOUNT("1", "Fixed Amount Voucher", "Enter the fixed amount for the voucher:"),
    PERCENT_DISCOUNT("2", "Percent Discount Voucher", "Enter the percent discount for the voucher:");

    private final String id;
    private final String name;
    private final String promptMessage;

    public static VoucherType matchType(int type) {
        return Arrays.stream(values())
                .filter(v -> type == Integer.parseInt(v.id))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
