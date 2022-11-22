package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.global.io.Message;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final long discount;

    public FixedAmountVoucher(UUID uuid, long discount) {
        super(uuid, VoucherType.FIXED_AMOUNT);

        if (discount < 0) {
            throw new RuntimeException(Message.BAD_DISCOUNT.getMessage());
        }

        this.discount = discount;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Type: %s Discount: %d", super.getId(), super.getType(), this.discount);
    }

    @Override
    public Long getDiscount(Long price) {
        return price - discount;
    }
}
