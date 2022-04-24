package com.mountain.voucherApp.shared.enums;

import com.mountain.voucherApp.domain.FixedAmountVoucher;
import com.mountain.voucherApp.domain.PercentDiscountVoucher;
import com.mountain.voucherApp.domain.Voucher;

import static com.mountain.voucherApp.shared.constants.Message.*;

public enum DiscountPolicy {
    FIXED(1, FIXED_DISCOUNT, FIXED_UINT, new FixedAmountVoucher()),
    PERCENT(2, PERCENT_DISCOUNT, PERCENT_UNIT, new PercentDiscountVoucher());

    private final int policyId;
    private final String description;
    private final String unit;
    private final Voucher voucher;

    DiscountPolicy(int policyId, String description, String unit, Voucher voucher) {
        this.policyId = policyId;
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

    public int getPolicyId() {
        return policyId;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}

