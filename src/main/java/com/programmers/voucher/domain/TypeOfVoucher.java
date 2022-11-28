package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

public enum TypeOfVoucher {
    FIXED_AMOUNT_VOUCHER("FixedAmount", "Insert amount of fixed discount", 6, (amount) -> validateFixedAmount(amount)),
    PERCENT_DISCOUNT_VOUCHER("PercentDiscount", "Insert ratio of discount", 3, (discountPercent) -> validatePercentDiscount(discountPercent)),;

    private final String type;
    private final String  guideMessage;
    private final int expirationPeriod;
    private final Consumer<Long> consumer;

    TypeOfVoucher(String type, String guideMessage, int expirationPeriod, Consumer<Long> consumer) {
        this.type = type;
        this.guideMessage = guideMessage;
        this.expirationPeriod = expirationPeriod;
        this.consumer = consumer;
    }


    public static Voucher createVoucher(String email ,TypeOfVoucher typeOfVoucher, long discount) {
        return new Voucher(UUID.randomUUID(), typeOfVoucher, discount, LocalDateTime.now(), LocalDateTime.now().plusMonths(typeOfVoucher.expirationPeriod), email);
    }
    public static void validateVoucher(TypeOfVoucher typeOfVoucher, long discount) {
        typeOfVoucher.consumer.accept(discount);
    }

    public static TypeOfVoucher getType(String typeName) {
        return Arrays.stream(TypeOfVoucher.values())
                .filter(typeOfVoucher -> typeOfVoucher.type.equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));
    }

    public static int getExpirationPeriod(TypeOfVoucher typeOfVoucher) {
        return typeOfVoucher.expirationPeriod;
    }

    public static String getType(TypeOfVoucher typeOfVoucher) {
        return typeOfVoucher.type;
    }

    public static String getGuideMessage(TypeOfVoucher typeOfVoucher) {
        return typeOfVoucher.guideMessage;
    }
    public static void validateFixedAmount(long amount) {
        final long MIN_VOUCHER_AMOUNT = 0L;
        final long MAX_VOUCHER_AMOUNT = 10000L;
        if (amount <= MIN_VOUCHER_AMOUNT || amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("잘못된 범위의 할인 금액입니다.");
        }
    }

    public static void validatePercentDiscount(long discountPercent) {
        final long MIN_PERCENT = 0;
        final long MAX_PERCENT = 100;
        if (discountPercent <= MIN_PERCENT || discountPercent > MAX_PERCENT) {
            throw new IllegalArgumentException("잘못된 범위의 할인율입니다.");
        }
    }

}
