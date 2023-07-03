package kr.co.programmers.springbootbasic.customer.domain;

import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.UUID;

public abstract class Customer {
    private final UUID id;
    private final String name;
    private final CustomerStatus status;
    private final Wallet wallet;

    public Customer(UUID id, String name, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.wallet = null;
    }

    public Customer(UUID id, String name, CustomerStatus status, Wallet wallet) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.wallet = wallet;
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

    public Wallet getWallet() {
        return wallet;
    }
}
