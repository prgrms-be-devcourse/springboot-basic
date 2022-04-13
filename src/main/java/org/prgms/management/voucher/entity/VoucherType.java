package org.prgms.management.voucher.entity;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED("fixed") {
        @Override
        public Voucher create(UUID uuid, int discountNum,
                              String voucherName, String voucherType) {
            return FixedAmountVoucher.getFixedAmountVoucher(uuid, discountNum,
                    voucherName, voucherType);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher create(UUID uuid, int discountNum,
                              String voucherName, String voucherType) {
            return PercentAmountVoucher.getPercentAmountVoucher(uuid, discountNum,
                    voucherName, voucherType);
        }
    };

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static Optional<Voucher> createVoucher(UUID uuid, int discountNum,
                                                  String voucherName, String inputType) {
        Optional<VoucherType> voucherType = Arrays.stream(VoucherType.values())
                .filter(s -> s.type.equalsIgnoreCase(inputType))
                .findFirst();
        if (voucherType.isEmpty()) return Optional.empty();
        return Optional.ofNullable(voucherType.get().create(uuid, discountNum, voucherName, voucherType.get().name()));
    }

    public abstract Voucher create(UUID uuid, int discountNum,
                                   String voucherName, String voucherType);
}
