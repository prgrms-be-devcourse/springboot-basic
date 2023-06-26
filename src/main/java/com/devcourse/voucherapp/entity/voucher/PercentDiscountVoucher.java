package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

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
        return format("{0} | 비율 할인 | {1}%", voucherId, discountRate);
    }
}
