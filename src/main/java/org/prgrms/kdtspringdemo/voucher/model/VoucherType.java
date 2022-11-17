package org.prgrms.kdtspringdemo.voucher.model;

import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED(FixedAmountVoucher::new), PERCENT(PercentDiscountVoucher::new);

    final BiFunction<UUID, Long, Voucher> constructor;

    VoucherType(BiFunction<UUID, Long, Voucher> constructor) {
        this.constructor = constructor;
    }

    public static VoucherType getTypeByName(String string) throws IllegalArgumentException {
        try {
            return VoucherType.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 바우쳐 타입 입니다.");
        }
    }

    public Voucher newInstance(UUID id, Long value) {
        return this.constructor.apply(id, value);
    }
}
