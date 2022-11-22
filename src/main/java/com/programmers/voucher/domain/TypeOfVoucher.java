package com.programmers.voucher.domain;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum TypeOfVoucher {
    FIXED_AMOUNT_VOUCHER("1","Insert amount of fixed discount",(discount)-> new FixedAmountVoucher(UUID.randomUUID(),discount)),
    PERCENT_DISCOUNT_VOUCHER("2","Insert ratio of discount" ,(discount) -> new PercentDiscountVoucher(UUID.randomUUID(), discount));


    private final String type;
    private final String  guideMessage;
    private final Function<Long, Voucher> function;
    TypeOfVoucher(String type,String guideMessage, Function<Long,Voucher> function) {
        this.type = type;
        this.guideMessage = guideMessage;
        this.function = function;
    }

    public static String getGuideMessage(TypeOfVoucher typeOfVoucher) {
        return typeOfVoucher.guideMessage;
    }

    public static Voucher createVoucher(TypeOfVoucher typeOfVoucher, long discount) {
        return typeOfVoucher.function.apply(discount);
    }

    public static TypeOfVoucher getType(String typeNumber) {
        return Arrays.stream(TypeOfVoucher.values())
                .filter(typeOfVoucher -> typeOfVoucher.type.equals(typeNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));
    }
}
