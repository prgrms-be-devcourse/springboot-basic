package org.prgms.management.entity;

import org.prgms.management.validator.FixedAmountVoucherValidator;
import org.prgms.management.validator.PercentAmountVoucherValidator;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED("fixed") {
        @Override
        public Voucher create(UUID uuid, int discountNum,
                              String voucherName, String voucherType) {
            FixedAmountVoucherValidator validator =
                    new FixedAmountVoucherValidator();

            if (!validator.validate(voucherName, discountNum)) return null;

            return FixedAmountVoucher.getFixedAmountVoucher(uuid, discountNum,
                    voucherName, voucherType);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher create(UUID uuid, int discountNum,
                              String voucherName, String voucherType) {
            PercentAmountVoucherValidator validator =
                    new PercentAmountVoucherValidator();

            if (!validator.validate(voucherName, discountNum)) return null;

            return new PercentAmountVoucher(uuid, discountNum,
                    voucherName, voucherType);
        }
    };

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static Voucher createVoucher(UUID uuid, int discountNum,
                                        String voucherName, String inputType) {
        Optional<VoucherType> voucherType = Arrays.stream(VoucherType.values())
                .filter(s -> s.type.equalsIgnoreCase(inputType))
                .findFirst();
        if (voucherType.isEmpty()) return null;
        return voucherType.get().create(uuid, discountNum, voucherName, voucherType.get().name());
    }

    public abstract Voucher create(UUID uuid, int discountNum,
                                   String voucherName, String voucherType);
}
