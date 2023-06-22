package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.VoucherFactory;

import java.util.Arrays;
import java.util.function.Supplier;

public enum VoucherType {
    FIXED_AMOUNT("1", "Amount", FixedVoucherFactory::new),
    PERCENT_DISCOUNT("2", "Percent", PercentVoucherFactory::new);

    private String typeOrdinal;
    private String typeString;
    private Supplier<VoucherFactory> supplier;

    VoucherType(String typeOrdinal, String typeString, Supplier<VoucherFactory> supplier) {
        this.typeOrdinal = typeOrdinal;
        this.typeString = typeString;
        this.supplier = supplier;
    }

    public static VoucherType getVoucherType(String voucherTypeString) {
        return Arrays.stream(VoucherType.values())
                .filter(vt -> vt.typeOrdinal.equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(InvalidDataException.INVALID_VOUCHER_TYPE));
    }

    public String getTypeString() {
        return typeString;
    }

    public VoucherFactory getVoucherFactory() {
        return supplier.get();
    }
}
