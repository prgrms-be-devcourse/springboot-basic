package org.programmers.springboot.basic.domain.wallet.entity;

import java.util.UUID;

public class Wallet {

    private final UUID voucherId;
    private final String email;

    public Wallet(UUID voucherId, String email) {
        this.voucherId = voucherId;
        this.email = email;
    }


    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmail() {
        return email;
    }
}
