package com.wonu606.vouchermanager.domain;

import java.util.UUID;

public abstract class Voucher {

    private final UUID uuid;

    protected Voucher(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public abstract double calculateDiscountedPrice(double originalPrice);
}
