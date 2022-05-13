package com.mountain.voucherApp.model.enums;

import com.mountain.voucherApp.model.vo.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.model.vo.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.model.vo.voucher.Voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mountain.voucherApp.common.constants.ProgramMessage.*;

public enum DiscountPolicy {
    FIXED(1, FIXED_DISCOUNT, FIXED_UINT, new FixedAmountVoucher()),
    PERCENT(2, PERCENT_DISCOUNT, PERCENT_UNIT, new PercentDiscountVoucher());

    public static final Map<Integer, DiscountPolicy> discountPolicies =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toConcurrentMap(DiscountPolicy::getSeq, Function.identity())));
    private final String description;
    private final String unit;
    private final Voucher voucher;
    private final int seq;

    DiscountPolicy(int seq, String description, String unit, Voucher voucher) {
        this.seq = seq;
        this.description = description;
        this.unit = unit;
        this.voucher = voucher;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public static Optional<DiscountPolicy> find(int seq) {
        return Optional.ofNullable(discountPolicies.get(seq));
    }

    public int getSeq() {
        return seq;
    }
}

