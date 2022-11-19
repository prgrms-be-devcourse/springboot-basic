package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long discount;

    public FixedAmountVoucher(UUID uuid, Long discount) {
        super(uuid, VoucherType.FIXED_AMOUNT);
        this.discount = discount;
    }

    @Override
    public Long getDiscount(Long price) {
        return price - discount;
    }
}
