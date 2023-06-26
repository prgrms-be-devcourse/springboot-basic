package com.devcourse.voucherapp.entity.voucher;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FixDiscountVoucher implements Voucher {

    @Getter
    private final UUID voucherId;

    private final int discountPrice;

    @Override
    public String toString() {
        return voucherId + ", 고정 할인, " + discountPrice + "원";
    }
}
