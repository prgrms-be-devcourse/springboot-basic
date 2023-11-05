package com.prgms.vouchermanager.domain.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {

    private Long id;
    private final Long customerId;
    private final UUID voucherId;

    public Wallet(Long id, Long customerId, UUID voucherId) {
        this.id = id;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", voucherId=" + voucherId +
                '}';
    }
}
