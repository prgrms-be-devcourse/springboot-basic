package org.prgrms.voucherapplication.voucher.entity;

import java.util.UUID;

public abstract class Voucher {

    protected UUID uuid;
    protected int discount;

    protected Voucher(UUID uuid, int discount) {
        this.uuid = uuid;
        this.discount = discount;
    }

    public UUID getUuid() {
        return uuid;
    }
}
