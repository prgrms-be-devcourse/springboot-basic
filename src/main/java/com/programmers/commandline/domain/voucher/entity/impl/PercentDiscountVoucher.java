package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final Long discount;
    public PercentDiscountVoucher(UUID uuid, Long discount) {
        super(uuid, VoucherType.PERCENT_DISCOUNT);
        this.discount = discount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Type: %s Discount: %d", super.getId(), super.getType(), this.discount);
    }

    @Override
    public Long getDiscount(Long price) {
        return price - (price * (discount/100));
    }
}
