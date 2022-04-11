package org.programmers.springbootbasic.voucher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public enum VoucherType {

    FIXED("정량 할인 바우처", FixedDiscountVoucher.class, discountUnit.AMOUNT.getName()),
    RATE("비율 할인 바우처", RateDiscountVoucher.class, discountUnit.PERCENT.getName());

    private final String name;
    private final Class<? extends Voucher> type;
    private final String discountUnitName;


    public static List<String> getAllNames() {
        List<String> voucherNames = new ArrayList<>();
        Arrays.stream(VoucherType.values()).forEach(
                voucherType -> voucherNames.add(voucherType.getName()));
        return voucherNames;
    }

    public String getName() {
        return name;
    }

    @RequiredArgsConstructor
    @Getter(AccessLevel.PRIVATE)
    private enum discountUnit {
        AMOUNT("할인액"), PERCENT("할인율");

        private final String name;
    }
}
