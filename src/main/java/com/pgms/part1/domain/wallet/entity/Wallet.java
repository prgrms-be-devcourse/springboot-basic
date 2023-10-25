package com.pgms.part1.domain.wallet.entity;

public class Wallet {

    private final Long voucherId;

    private final Long customerId;

    public Wallet(Long voucherId, Long customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

}
