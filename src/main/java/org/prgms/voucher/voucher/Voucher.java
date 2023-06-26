package org.prgms.voucher.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Voucher {

    private final UUID id;

    protected Voucher(UUID id) {
        this.id = id;
    }

    abstract long discount(long price);

}
