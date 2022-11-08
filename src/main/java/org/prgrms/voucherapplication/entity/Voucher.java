package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public abstract class Voucher {

    private UUID uuid;

    public Voucher(UUID uuid) {
        this.uuid = uuid;
    }
}
