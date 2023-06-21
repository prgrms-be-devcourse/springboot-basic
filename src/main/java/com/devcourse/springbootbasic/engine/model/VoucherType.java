package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.voucher.domain.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.domain.factory.VoucherFactory;

import java.util.function.Supplier;

public enum VoucherType {
    FIXED_AMOUNT("Amount", FixedVoucherFactory::new),
    PERCENT_DISCOUNT("Percent", PercentVoucherFactory::new);

    private String typeString;
    private Supplier<VoucherFactory> supplier;

    VoucherType(String typeString, Supplier<VoucherFactory> supplier) {
        this.typeString = typeString;
        this.supplier = supplier;
    }

    public String getTypeString() {
        return typeString;
    }

    public VoucherFactory getVoucherFactory() {
        return supplier.get();
    }
}
