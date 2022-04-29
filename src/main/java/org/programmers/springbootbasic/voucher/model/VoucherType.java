package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED(1, "FIXED"){
        @Override
        public Voucher create(UUID voucherId, long value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, value, createdAt);
        }
    },
    PERCENT(2, "PERCENT") {
        @Override
        public Voucher create(UUID voucherId, long value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, value, createdAt);
        }
    };

    private final int number;
    private final String type;

    VoucherType(int number, String type) {
        this.number = number;
        this.type = type;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.equalsNumber(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 넘버입니다."));
    }

    public static VoucherType findByType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
    }

    public abstract Voucher create(UUID voucherId, long value, LocalDateTime createdAt);

    private boolean equalsNumber(int findNumber) {
        return (this.number == findNumber);
    }
}
