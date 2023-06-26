package com.devcourse.voucherapp.entity.voucher;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    @Getter
    private final UUID voucherId;

    private final int discountRate;

    @Override
    public String toString() {
        return voucherId + ", 비율 할인, " + discountRate + "%";
    }
}
