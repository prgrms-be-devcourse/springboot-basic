package org.prgms.management.voucher.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
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

    public static Optional<Voucher> createVoucher(
            UUID uuid, int discountNum, String voucherName, String inputType, LocalDateTime createdAt) {
        Optional<VoucherCreator> voucherCreator = Arrays.stream(VoucherCreator.values())
                .filter(s -> s.type.equalsIgnoreCase(inputType))
                .findFirst();

        if (voucherCreator.isEmpty()) return Optional.empty();

        return Optional.ofNullable(
                voucherCreator.get().create(uuid, discountNum, voucherName,
                        voucherCreator.get().name(), createdAt));
    }

    public abstract Voucher create(
            UUID uuid, int discountNum, String voucherName, String voucherType, LocalDateTime createdAt);
}
