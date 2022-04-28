package org.prgms.management.model.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherCreator {
    FIXED("fixed") {
        @Override
        public Voucher create(UUID uuid, int discountNum, String voucherName,
                              String voucherType, LocalDateTime createdAt) {
            return FixedAmountVoucher.getFixedAmountVoucher(uuid, discountNum,
                    voucherName, voucherType, createdAt);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher create(UUID uuid, int discountNum, String voucherName,
                              String voucherType, LocalDateTime createdAt) {
            return PercentAmountVoucher.getPercentAmountVoucher(uuid, discountNum,
                    voucherName, voucherType, createdAt);
        }
    };

    private final String type;

    VoucherCreator(String type) {
        this.type = type;
    }

    public static Voucher createVoucher(
            UUID uuid, int discountNum, String voucherName, String inputType, LocalDateTime createdAt) {
        var voucherCreator = Arrays.stream(VoucherCreator.values())
                .filter(s -> s.type.equalsIgnoreCase(inputType))
                .findFirst();

        if (voucherCreator.isEmpty()) {
            throw new IllegalArgumentException("잘못된 타입을 입력했습니다.");
        }

        return voucherCreator.get().create(uuid, discountNum, voucherName,
                voucherCreator.get().name(), createdAt);
    }

    public static Voucher createVoucher(
            int discountNum, String voucherName, String inputType) {
        var voucherCreator = Arrays.stream(VoucherCreator.values())
                .filter(s -> s.type.equalsIgnoreCase(inputType))
                .findFirst();

        if (voucherCreator.isEmpty()) {
            throw new IllegalArgumentException("잘못된 타입을 입력했습니다.");
        }

        return voucherCreator.get().create(UUID.randomUUID(), discountNum, voucherName,
                voucherCreator.get().name(), LocalDateTime.now());
    }

    public abstract Voucher create(
            UUID uuid, int discountNum, String voucherName, String voucherType, LocalDateTime createdAt);
}
