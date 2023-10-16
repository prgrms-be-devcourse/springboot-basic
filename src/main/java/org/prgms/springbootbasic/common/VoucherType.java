package org.prgms.springbootbasic.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.Voucher;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public enum VoucherType {
    FIXED_AMOUNT(1, FixedAmountVoucher::new, "FixedAmountVoucher"),
    PERCENT_DISCOUNT(2, PercentDiscountVoucher::new, "PercentDiscountVoucher");

    private final int seq;
    private final BiFunction<UUID, Long, Voucher> biFunction;
    @Getter
    private final String displayName;

    VoucherType(int seq, BiFunction<UUID, Long, Voucher> biFunction, String displayName) {
        this.seq = seq;
        this.biFunction = biFunction;
        this.displayName = displayName;
    }

    public static VoucherType getTypeFromSeq(int seq){
        for (VoucherType type : values()){
            if (type.seq == seq)
                return type;
        }
        log.warn("user input = {}", seq);
        throw new IllegalArgumentException("Invalid seq");
    }

    public Voucher create(long val){
        return this.biFunction.apply(UUID.randomUUID(), val);
    }

    public Voucher create(UUID uuid, long val){
        return this.biFunction.apply(uuid, val);
    }
}
