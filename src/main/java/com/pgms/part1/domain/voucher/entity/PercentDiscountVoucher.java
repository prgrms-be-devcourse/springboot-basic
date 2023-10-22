package com.pgms.part1.domain.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(UUID id, Integer discount) {
        super(id, discount, VoucherDiscountType.PERCENT_DISCOUNT);
    }
}
