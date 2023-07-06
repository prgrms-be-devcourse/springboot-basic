package com.programmers.voucher.entity.voucher;

import java.util.UUID;

public class Voucher {
    private final UUID id;
    private VoucherType type;
    private int amount;

    public Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, int amount) {
        return new Voucher(UUID.randomUUID(), type, amount);
    }

    public void update(VoucherType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public long discount(long price) {
        return type.discount(price, amount);
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
