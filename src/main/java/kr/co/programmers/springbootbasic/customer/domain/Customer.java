package kr.co.programmers.springbootbasic.customer.domain;

import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.UUID;

public abstract class Customer {
    private final UUID id;
    private final String name;
    private final CustomerStatus status;
    private final UUID walletId;

    public Customer(UUID id, String name, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.walletId = null;
    }

    public Customer(UUID id, String name, CustomerStatus status, UUID walletId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.walletId = walletId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public UUID getWalletId() {
        return walletId;
    }
}
