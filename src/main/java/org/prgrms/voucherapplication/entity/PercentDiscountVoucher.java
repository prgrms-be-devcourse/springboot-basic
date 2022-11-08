package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    private int percent;

    public PercentDiscountVoucher(UUID uuid, int percent) {
        super(uuid);
        this.percent = percent;
    }
}
