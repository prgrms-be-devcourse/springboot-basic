package com.prgrms.spring.domain.voucher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PercentDiscountVoucher implements Voucher {
    private final String voucherName = "PercentDiscountVoucher";
    private final UUID voucherId;
    private final long discount;
    private final String discountUnit = "%";

    public static PercentDiscountVoucher newInstance(UUID voucherId, long discount) {
        if (discount <= 0 || discount >= 100) throw new IllegalArgumentException("유효하지 않은 할인 금액");
        return new PercentDiscountVoucher(voucherId, discount);
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
}
