package org.prgms.springbootbasic.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.voucher.FixedAmountPolicy;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountPolicy;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;

import java.util.function.Supplier;

import static org.prgms.springbootbasic.common.CommonConstant.INPUT_FIXED_AMOUNT_VOUCHER;
import static org.prgms.springbootbasic.common.CommonConstant.INPUT_PERCENT_DISCOUNT_VOUCHER;

@Slf4j
public enum VoucherType {
    FIXED_AMOUNT(INPUT_FIXED_AMOUNT_VOUCHER, FixedAmountPolicy::new, "FixedAmountPolicy"),
    PERCENT_DISCOUNT(INPUT_PERCENT_DISCOUNT_VOUCHER, PercentDiscountPolicy::new, "PercentDiscountPolicy");

    private final int seq;
    private final Supplier<VoucherPolicy> createVoucherPolicy;
    @Getter
    private final String displayName;

    VoucherType(int seq, Supplier<VoucherPolicy> createVoucherPolicy, String displayName) {
        this.seq = seq;
        this.createVoucherPolicy = createVoucherPolicy;
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

    public static VoucherType getTypeFromName(String policyName){
        for (VoucherType type : values()){
            if (type.displayName.equals(policyName)) {
                return type;
            }
        }

        log.warn("user input policy = {}", policyName);
        throw new IllegalArgumentException("Invalid policy");
    }

    public VoucherPolicy create(){
        return this.createVoucherPolicy.get();
    }
}
