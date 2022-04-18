package org.prgrms.kdt.model.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1) {
        @Override
        public Voucher newVoucher(UUID voucherId, int discountAmount) {
            return new FixedAmountVoucher(voucherId, discountAmount);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher newVoucher(UUID voucherId, int discountAmount) {
            return new PercentDiscountVoucher(voucherId, discountAmount);
        }
    };

    private final Integer typeNumber;

    VoucherType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public Integer getTypeNumber() {
        return typeNumber;
    }

    public abstract Voucher newVoucher(UUID voucherId, int discountAmount);

    public static Optional<VoucherType> getVoucherTypeByNumber(int voucherTypeNumber) {
        return Arrays.stream(VoucherType.values())
                .filter(t -> t.getTypeNumber() == voucherTypeNumber)
                .findAny();
    }
}
