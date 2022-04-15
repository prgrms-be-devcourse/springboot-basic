package com.voucher.vouchermanagement.model.voucher;

import com.voucher.vouchermanagement.service.CreateVoucherDto;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
    Fixed("FixedAmountVoucher", 1, FixedAmountVoucher::createVoucher),
    Percent("PercentDiscountVoucher", 2, PercentDiscountVoucher::createVoucher);

    private final String typeName;
    private final int typeNumber;
    private final Function<CreateVoucherDto, Voucher> constructor;

    VoucherType(String typeName, int typeNumber, Function<CreateVoucherDto, Voucher> constructor) {
        this.typeName = typeName;
        this.typeNumber = typeNumber;
        this.constructor = constructor;
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
                .findFirst().orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입니다."));
    }

    public static VoucherType getVoucherTypeByNumber(int typeNumberInput) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getTypeNumber() == typeNumberInput)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입니다."));
    }

    public Voucher create(CreateVoucherDto createVoucherDto) {
        return this.constructor.apply(createVoucherDto);
    }
}
