package org.prgms.springbootbasic.common;

import org.prgms.springbootbasic.domain.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.Voucher;

import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED_AMOUNT(1, val -> new FixedAmountVoucher(UUID.randomUUID(), val)),
    PERCENT_DISCOUNT(2, val -> new PercentDiscountVoucher(UUID.randomUUID(), val));

    private final int seq;
    private final Function<Long, Voucher> consumer;

    VoucherType(int seq, Function<Long, Voucher> consumer) {
        this.seq = seq;
        this.consumer = consumer;
    }

    public static VoucherType getTypeFromSeq(int seq){
        for (VoucherType type : values()){
            if (type.seq == seq)
                return type;
        }
        throw new IllegalArgumentException("Invalid seq");
    }

    public Voucher create(long val){
        return this.consumer.apply(val);
    }
}
