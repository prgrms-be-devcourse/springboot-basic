package com.prgrms.vouchermanagement.core.wallet.domain;

import java.util.Objects;
import java.util.UUID;

public class Wallet {

    private final String id;
    private final String customerId;
    private final String voucherId;

    public Wallet(String customerId, String voucherId) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Wallet(String id, String customerId, String voucherId) {
        this.id = id;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(customerId, wallet.customerId) && Objects.equals(voucherId, wallet.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, voucherId);
    }
}
