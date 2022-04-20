package com.voucher.vouchermanagement.model.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    Fixed("FixedAmountVoucher", 1) {
        @Override
        public Voucher create(UUID voucherId, long voucherValue, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, voucherValue, createdAt);
        }
    },
    Percent("PercentDiscountVoucher", 2) {
        @Override
        public Voucher create(UUID voucherId, long voucherValue, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, voucherValue, createdAt);
        }
    };

    private final String typeName;
    private final int typeNumber;

    VoucherType(String typeName, int typeNumber) {
        this.typeName = typeName;
        this.typeNumber = typeNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getTypeNumber() {
        return typeNumber;
    }

    public static VoucherType getVoucherTypeByName(String typeNameInput) {
        return Arrays.stream(VoucherType.values())
                .filter((voucherType) -> voucherType.getTypeName().equals(typeNameInput))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입니다."));
    }

    public static VoucherType getVoucherTypeByNumber(int typeNumberInput) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getTypeNumber() == typeNumberInput)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입니다."));
    }

    @Override
    public String toString() {
        return typeNumber + ". " + typeName;
    }

    public abstract Voucher create(UUID voucherId, long voucherValue, LocalDateTime createdAt);
}
