package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    private int discount;

    public FixedAmountVoucher(UUID uuid, int discount) {
        super(uuid);
        this.discount = discount;
    }
}
