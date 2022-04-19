package com.mountain.voucherApp.enums;

import com.mountain.voucherApp.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;

import static com.mountain.voucherApp.constants.Message.FIXED_DISCOUNT;
import static com.mountain.voucherApp.constants.Message.PERCENT_DISCOUNT;

public enum DiscountPolicy {
    FIXED(1, FIXED_DISCOUNT, new FixedAmountVoucher()),
    PERCENT(2, PERCENT_DISCOUNT, new PercentDiscountVoucher());

    private final int policyId;
    private final String description;
    private final Voucher voucher;

    DiscountPolicy(int policyId, String description, Voucher voucher) {
        this.policyId = policyId;
        this.description = description;
        this.voucher = voucher;
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

