package org.prgms.springbootbasic.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;

import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
public enum VoucherType {
    FIXED_AMOUNT(1, FixedAmountVoucher::new, "FixedAmountVoucher"),
    PERCENT_DISCOUNT(2, PercentDiscountVoucher::new, "PercentDiscountVoucher");

    private final int seq;
    private final BiFunction<UUID, Long, VoucherPolicy> biFunction;
    @Getter
    private final String displayName;

    VoucherType(int seq, BiFunction<UUID, Long, VoucherPolicy> biFunction, String displayName) {
        this.seq = seq;
        this.biFunction = biFunction;
        this.displayName = displayName;
    }

    public static VoucherType getTypeFromSeq(int seq){
        for (VoucherType type : values()){
            if (type.seq == seq) {
                return type;
            }
        }

        log.warn("user input = {}", seq);
        throw new IllegalArgumentException("Invalid seq");
    }

    public VoucherPolicy create(long discountDegree){
        return this.biFunction.apply(UUID.randomUUID(), discountDegree);
    }

    public VoucherPolicy create(UUID uuid, long discountDegree){
        return this.biFunction.apply(uuid, discountDegree);
    }
}
