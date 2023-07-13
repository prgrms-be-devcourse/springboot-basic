package com.prgrms.spring.domain.voucher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FixedAmountVoucher implements Voucher {
    private final String voucherName = "FixedAmountVoucher";
    private final UUID voucherId;
    private final long discount;
    private final String discountUnit = "$";

    public static FixedAmountVoucher newInstance(UUID voucherId, long discount) {
        chkDiscountValue(discount);
        return new FixedAmountVoucher(voucherId, discount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public String getDiscountUnit() {
        return discountUnit;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }

    private static void chkDiscountValue(long discount) {
        if (discount <= 0) throw new IllegalArgumentException("유효하지 않은 할인 금액");
    }
}
