package com.devcourse.voucherapp.entity.voucher;

import static java.text.MessageFormat.format;

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
        return format("{0} | 고정 할인 | {1}원", voucherId, discountPrice);
    }
}
