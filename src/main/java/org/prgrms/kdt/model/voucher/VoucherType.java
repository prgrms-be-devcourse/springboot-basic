package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1) {
        @Override
        public Voucher createNewVoucher(UUID voucherId, int discountAmount, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, discountAmount, createdAt);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher createNewVoucher(UUID voucherId, int discountAmount, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, discountAmount, createdAt);
        }
    };

    private final Integer typeNumber;

    VoucherType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public Integer getTypeNumber() {
        return typeNumber;
    }

    public abstract Voucher createNewVoucher(UUID voucherId, int discountAmount, LocalDateTime createdAt);

    public static Optional<VoucherType> getVoucherTypeByNumber(int voucherTypeNumber) {
        return Arrays.stream(VoucherType.values())
                .filter(t -> t.getTypeNumber() == voucherTypeNumber)
                .findAny();
    }
}
