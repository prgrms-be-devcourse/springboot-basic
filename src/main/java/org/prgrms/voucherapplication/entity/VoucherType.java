package org.prgrms.voucherapplication.entity;

import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED(1,"원하시는 할인 금액을 입력해주세요.", "fixed", discount -> new FixedAmountVoucher(UUID.randomUUID(), discount)),
    PERCENT(2,"원하시는 할인 퍼센트를 입력해주세요.", "percent", discount -> new PercentDiscountVoucher(UUID.randomUUID(), discount));

    private static final String NOT_EXIST = "없는 바우처 종류입니다.";
    private final int order;
    private final String discountGuide;
    private final String name;
    private final Function<Integer, Voucher> constructor;

    VoucherType(int order, String discountGuide, String name, Function<Integer, Voucher> constructor) {
        this.order = order;
        this.discountGuide = discountGuide;
        this.name = name;
        this.constructor = constructor;
    }

    public String getDiscountGuide() {
        return discountGuide;
    }

    public static String getNames() {
        StringBuilder names = new StringBuilder();
        VoucherType[] values = VoucherType.values();
        for (VoucherType voucherType : values) {
            names.append(voucherType.order).append(". ").append(voucherType.name).append(" ");
        }
        return names.toString();
    }

    public static VoucherType of(String name) {
        VoucherType[] values = VoucherType.values();
        for (VoucherType voucherType : values) {
            if (voucherType.name.equals(name)) {
                return voucherType;
            }
        }

        throw new IllegalArgumentException(NOT_EXIST);
    }

    public Voucher createVoucher(int discount) {
        return this.constructor.apply(discount);
    }
}
